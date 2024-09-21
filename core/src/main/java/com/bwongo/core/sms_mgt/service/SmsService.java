package com.bwongo.core.sms_mgt.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.account_mgt.models.jpa.TAccountTransaction;
import com.bwongo.core.account_mgt.models.jpa.TCashflow;
import com.bwongo.core.account_mgt.repository.TAccountRepository;
import com.bwongo.core.account_mgt.repository.TAccountTransactionRepository;
import com.bwongo.core.account_mgt.repository.TCashflowRepository;
import com.bwongo.core.base.model.enums.*;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantSmsSetting;
import com.bwongo.core.merchant_mgt.repository.TMerchantRepository;
import com.bwongo.core.merchant_mgt.repository.TMerchantSmsSettingRepository;
import com.bwongo.core.sms_mgt.models.dto.request.SmsRequestDto;
import com.bwongo.core.sms_mgt.models.dto.response.SmsResponseDto;
import com.bwongo.core.sms_mgt.models.jpa.TSms;
import com.bwongo.core.sms_mgt.repository.TSmsRepository;
import com.bwongo.core.sms_mgt.service.dto.SmsDtoService;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.bwongo.core.account_mgt.utils.AccountMsgUtils.NOT_CREDIT_ACCOUNT;
import static com.bwongo.core.account_mgt.utils.AccountUtils.checkIfAccountIsValid;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.MERCHANT_NOT_FOUND;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.MERCHANT_SMS_SETTING_NOT_FOUND;
import static com.bwongo.core.sms_mgt.utils.SmsMsgConstants.*;
import static com.bwongo.core.sms_mgt.utils.SmsUtils.checkIfSmsCanBeResent;
import static com.bwongo.core.sms_mgt.utils.SmsUtils.getInternalReference;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 9:16 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final TSmsRepository smsRepository;
    private final SmsDtoService smsDtoService;
    private final AuditService auditService;
    private final TUserRepository userRepository;
    private final TMerchantRepository merchantRepository;
    private final TAccountRepository accountRepository;
    private final TMerchantSmsSettingRepository merchantSmsSettingRepository;
    private final TAccountTransactionRepository accountTransactionRepository;
    private final TCashflowRepository cashflowRepository;

    private static final String SMS = "sms";

    @Value("${app.system-account-code.sms}")
    private String smsSystemAccountCode;

    public SmsResponseDto sendSms(SmsRequestDto smsRequestDto) {

        smsRequestDto.validate();
        var merchant = getLoggedInUserMerchant();
        var account = getMerchantAccount();
        var smsCost = getMerchantSmsSetting(merchant).getSmsCost();

        checkIfAccountIsValid.accept(account, smsCost);

        var sms = smsDtoService.dtoToSms(smsRequestDto);
        sms.setMerchant(merchant);
        sms.setSmsStatus(SmsStatusEnum.PENDING);
        sms.setResend(Boolean.FALSE);
        sms.setInternalReference(getInternalReference.apply(merchant.getMerchantCode()));
        sms.setResendCount(0);
        sms.setCost(smsCost);

        auditService.stampAuditedEntity(sms);
        var savedSms = smsRepository.save(sms);

        return smsDtoService.smsToDto(savedSms);
    }

    public SmsResponseDto resendSms(Long id) {
        var sms = getSmsById(id);
        var resendCount = sms.getResendCount();

        checkIfSmsCanBeResent.accept(sms);

        sms.setResend(Boolean.TRUE);
        sms.setResendCount(resendCount + 1);
        sms.setSmsStatus(SmsStatusEnum.PENDING);

        auditService.stampAuditedEntity(sms);

        //PUSH SMS KAFKA

        //SMS TRANSACTION FOR PUSHED SMS


        return smsDtoService.smsToDto(sms);
    }

    public void reverseTransactionForNonDeliveredSms(TSms sms){

    }

    @Transactional
    public void transactForPushedSms(TSms sms){
        var merchant = sms.getMerchant();
        var merchantSmsSetting = merchantSmsSettingRepository.findByMerchant(merchant).get();
        var smsCost = getMerchantSmsSetting(merchant).getSmsCost();
        var merchantDebitAccount = getAccountByMerchant(merchant, AccountTypeEnum.DEBIT);
        var merchantAccountToBeUsed = (merchantSmsSetting.getPaymentType().equals(PaymentTypeEnum.POSTPAID) && (merchantDebitAccount.getCurrentBalance().compareTo(smsCost) < 0 )) ? getAccountByMerchant(merchant, AccountTypeEnum.CREDIT) : merchantDebitAccount;
        var smsSystemAccount = accountRepository.findByCode(smsSystemAccountCode).get();

        if(merchantAccountToBeUsed.getAccountType().equals(PaymentTypeEnum.PREPAID)) {
            transferFundsFromAccountToAccount(merchantAccountToBeUsed, smsSystemAccount, smsCost, sms);
        }else{
            transactionOnCreditAccount(merchantAccountToBeUsed, sms);
        }

    }

    @Transactional
    protected void transactionOnCreditAccount(TAccount creditAccount, TSms sms){

        var externalReference = sms.getExternalReference();
        var smsCost = sms.getCost();

        sms.setPaymentStatus(PaymentStatusEnum.NOT_PAID);
        auditService.stampLongEntity(sms);

        smsRepository.save(sms);

        Validate.isTrue(creditAccount.getAccountType().equals(AccountTypeEnum.CREDIT), ExceptionType.BAD_REQUEST, NOT_CREDIT_ACCOUNT);

        var amountBefore = creditAccount.getCurrentBalance();
        var amountAfter = amountBefore.add(smsCost);

        creditAccount.setCurrentBalance(amountAfter);
        auditService.stampAuditedEntity(creditAccount);

        var updatedCreditAccount = accountRepository.save(creditAccount);

        var creditAccountTransaction = TAccountTransaction.builder()
                .account(updatedCreditAccount)
                .transactionType(TransactionTypeEnum.ACCOUNT_CREDIT)
                .nonReversal(Boolean.FALSE)
                .transactionStatus(TransactionStatusEnum.SUCCESSFUL)
                .balanceBefore(amountBefore)
                .balanceAfter(amountAfter)
                .statusDescription(SMS + TransactionTypeEnum.ACCOUNT_CREDIT.getDescription()+ " CREDIT AMOUNT")
                .externalTransactionId(externalReference)
                .build();

        auditService.stampLongEntity(creditAccountTransaction);
        accountTransactionRepository.save(creditAccountTransaction);
    }

    protected void transferFundsFromAccountToAccount(TAccount fromAccount, TAccount toAccount, BigDecimal amount, TSms sms) {

        var externalReference = sms.getExternalReference();

        //from account
        var fromCurrentBalance = fromAccount.getCurrentBalance();
        var fromAfterBalance = fromCurrentBalance.subtract(amount);

        fromAccount.setCurrentBalance(fromAfterBalance);

        auditService.stampLongEntity(fromAccount);
        var updatedFromAccount = accountRepository.save(fromAccount);

        //to account
        var toCurrentBalance = toAccount.getCurrentBalance();
        var toAfterBalance = toCurrentBalance.add(amount);

        toAccount.setCurrentBalance(toAfterBalance);

        auditService.stampLongEntity(toAccount);
        var updatedToAccount = accountRepository.save(toAccount);

        //from account transaction
        var fromAccountTransaction = TAccountTransaction.builder()
                .account(updatedFromAccount)
                .transactionType(TransactionTypeEnum.ACCOUNT_DEBIT)
                .nonReversal(Boolean.FALSE)
                .transactionStatus(TransactionStatusEnum.SUCCESSFUL)
                .balanceBefore(fromCurrentBalance)
                .balanceAfter(fromAfterBalance)
                .statusDescription(SMS + TransactionTypeEnum.ACCOUNT_DEBIT.getDescription())
                .externalTransactionId(externalReference)
                .build();

        auditService.stampLongEntity(updatedFromAccount);
        var savedFromAccountTransaction = accountTransactionRepository.save(fromAccountTransaction);

        //from account transaction
        var toAccountTransaction = TAccountTransaction.builder()
                .account(updatedToAccount)
                .transactionType(TransactionTypeEnum.ACCOUNT_CREDIT)
                .nonReversal(Boolean.FALSE)
                .transactionStatus(TransactionStatusEnum.SUCCESSFUL)
                .balanceBefore(toCurrentBalance)
                .balanceAfter(toAfterBalance)
                .statusDescription(SMS + TransactionTypeEnum.ACCOUNT_CREDIT.getDescription())
                .externalTransactionId(externalReference)
                .build();

        auditService.stampLongEntity(updatedToAccount);
        var savedToAccountTransaction = accountTransactionRepository.save(toAccountTransaction);

        //cash flow
        var cashFlowType = toAccount.getAccountCategory().equals(AccountCategoryEnum.SYSTEM) ? CashFlowEnum.BUSINESS_TO_MAIN : CashFlowEnum.MAIN_TO_BUSINESS;

        var cashFlow = TCashflow.builder()
                .externalReference(externalReference)
                .amount(amount)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .fromAccountTransaction(savedFromAccountTransaction)
                .toAccountTransaction(savedToAccountTransaction)
                .flowType(cashFlowType)
                .build();

        auditService.stampLongEntity(cashFlow);
        cashflowRepository.save(cashFlow);

        //UPDATE SMS TO PAID
        sms.setPaymentStatus(PaymentStatusEnum.PAID);
        auditService.stampLongEntity(sms);
        smsRepository.save(sms);
    }

    private TMerchantSmsSetting getMerchantSmsSetting(TMerchant merchant) {
        var existingSmsSettings = merchantSmsSettingRepository.findByMerchant(merchant);
        Validate.isPresent(existingSmsSettings, MERCHANT_SMS_SETTING_NOT_FOUND, merchant.getId());
        return existingSmsSettings.get();
    }

    private TAccount getMerchantAccount(){
        var merchant = getLoggedInUserMerchant();
        return getAccountByMerchant(merchant, AccountTypeEnum.DEBIT);
    }

    private TAccount getAccountByMerchant(TMerchant merchant, AccountTypeEnum accountType){
        var existingAccount = accountRepository.findByMerchantAndAccountType(merchant, accountType);
        Validate.isPresent(existingAccount, MERCHANT_NOT_FOUND, merchant.getId());
        return existingAccount.get();
    }

    private TMerchant getLoggedInUserMerchant() {
        return merchantRepository.findById(getLoggedInUser().getId()).get();
    }

    private TUser getLoggedInUser() {
        var loggedInUserId = auditService.getLoggedInUser().getId();
        return userRepository.findById(loggedInUserId).orElse(null);
    }

    private TSms getSmsById(Long smsId){
        var existingSms = smsRepository.findById(smsId);
        Validate.isPresent(existingSms, SMS_NOT_FOUND, smsId);
        return existingSms.get();
    }
}
