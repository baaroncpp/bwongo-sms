package com.bwongo.core.account_mgt.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.account_mgt.models.dto.request.MomoDepositRequestDto;
import com.bwongo.core.account_mgt.models.dto.response.AccountResponseDto;
import com.bwongo.core.account_mgt.models.dto.response.MomoDepositResponseDto;
import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.account_mgt.repository.TAccountRepository;
import com.bwongo.core.account_mgt.repository.TAccountTransactionRepository;
import com.bwongo.core.account_mgt.repository.TMomoDepositRepository;
import com.bwongo.core.account_mgt.service.dto.AccountDtoService;
import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.repository.TMerchantRepository;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.bwongo.core.base.utils.BaseUtils.pageToDto;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 7:52 PM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final TMomoDepositRepository momoDepositRepository;
    private final TMerchantRepository merchantRepository;
    private final AccountDtoService accountDtoService;
    private final TAccountTransactionRepository accountTransactionRepository;
    private final TAccountRepository accountRepository;
    private final AuditService auditService;
    private final TUserRepository userRepository;

    public MomoDepositResponseDto makeMomoDeposit(MomoDepositRequestDto momoDepositRequestDto){

        momoDepositRequestDto.validate();

        var merchant = getLoggedInUserMerchant();
        //TODO
        return null;
    }

    public void updateMomoPendingDeposits(){
        //TODO
    }

    public PageResponseDto getMerchantMomoDeposits(Long merchantId, Pageable pageable) {

        var merchant = getMerchantById(merchantId);

        var depositPage = momoDepositRepository.findAllByMerchant(merchant, pageable);
        var depositList = depositPage.stream()
                .map(accountDtoService::momoDepositToDto)
                .toList();

        return pageToDto(depositPage, depositList);
    }

    public PageResponseDto getMomoDeposits(Pageable pageable) {
        var depositPage = momoDepositRepository.findAll(pageable);
        var depositList = depositPage.stream()
                .map(accountDtoService::momoDepositToDto)
                .toList();

        return pageToDto(depositPage, depositList);
    }

    public PageResponseDto getMerchantTransactions(Long merchantId, Pageable pageable) {
        var merchant = getMerchantById(merchantId);

        var transactionPage = accountTransactionRepository.findAllByAccount(getAccountByMerchant(merchant), pageable);
        var transactionList = transactionPage.stream()
                .map(accountDtoService::accountTransactionToDto)
                .toList();

        return pageToDto(transactionPage, transactionList);
    }

    public PageResponseDto getAccountTransactions(Pageable pageable) {
        var transactionPage = accountTransactionRepository.findAll(pageable);
        var transactionList = transactionPage.stream()
                .map(accountDtoService::accountTransactionToDto)
                .toList();

        return pageToDto(transactionPage, transactionList);
    }

    public PageResponseDto getAccounts(Pageable pageable) {

        var accountPage = accountRepository.findAll(pageable);
        var accountList = accountPage.stream()
                .map(accountDtoService::accountToDto)
                .toList();

        return pageToDto(accountPage, accountList);
    }

    public AccountResponseDto getMerchantAccount(){
        return accountDtoService
                .accountToDto(getAccountByMerchant(getLoggedInUserMerchant()));
    }

    private TMerchant getMerchantById(Long id){
        var existingMerchant = merchantRepository.findById(id);
        Validate.isPresent(existingMerchant, MERCHANT_NOT_FOUND, id);
        return existingMerchant.get();
    }

    private TAccount getAccountByMerchant(TMerchant merchant){
        var existingAccount = accountRepository.findByMerchant(merchant);
        Validate.isPresent(existingAccount, MERCHANT_NOT_FOUND, merchant.getId());
        return existingAccount.get();
    }

    private TMerchant getLoggedInUserMerchant(){
        var userId = auditService.getLoggedInUser().getId();
        var existingUser = userRepository.findById(userId);
        var user = existingUser.get();
        var merchantId = user.getMerchantId();

        Validate.notNull(merchantId, ExceptionType.BAD_REQUEST, NOT_MERCHANT_USER);

        return getMerchantById(merchantId);
    }
}