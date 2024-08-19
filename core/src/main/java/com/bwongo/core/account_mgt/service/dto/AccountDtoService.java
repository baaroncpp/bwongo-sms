package com.bwongo.core.account_mgt.service.dto;

import com.bwongo.core.account_mgt.models.dto.request.MomoDepositRequestDto;
import com.bwongo.core.account_mgt.models.dto.response.AccountResponseDto;
import com.bwongo.core.account_mgt.models.dto.response.AccountTransactionResponseDto;
import com.bwongo.core.account_mgt.models.dto.response.CashFlowResponseDto;
import com.bwongo.core.account_mgt.models.dto.response.MomoDepositResponseDto;
import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.account_mgt.models.jpa.TAccountTransaction;
import com.bwongo.core.account_mgt.models.jpa.TCashflow;
import com.bwongo.core.account_mgt.models.jpa.TMomoDeposit;
import com.bwongo.core.merchant_mgt.service.dto.MerchantDtoService;
import com.bwongo.core.user_mgt.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 7:53 PM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountDtoService {

    private final UserDtoService userDtoService;
    private final MerchantDtoService merchantDtoService;

    public TMomoDeposit dtoToMomoDeposit(MomoDepositRequestDto momoDepositRequestDto) {

        if(momoDepositRequestDto == null)
            return null;

        return TMomoDeposit.builder()
                .amountDeposit(momoDepositRequestDto.amountDeposit())
                .depositorName(momoDepositRequestDto.depositorName())
                .msisdn(momoDepositRequestDto.msisdn())
                .build();
    }

    public MomoDepositResponseDto momoDepositToDto(TMomoDeposit momoDeposit) {

        if(momoDeposit == null)
            return null;

        return new MomoDepositResponseDto(
                momoDeposit.getId(),
                momoDeposit.getCreatedOn(),
                momoDeposit.getModifiedOn(),
                userDtoService.userToDto(momoDeposit.getCreatedBy()),
                userDtoService.userToDto(momoDeposit.getModifiedBy()),
                momoDeposit.getAmountDeposit(),
                momoDeposit.getTransactionStatus(),
                momoDeposit.getMsisdn(),
                momoDeposit.getExternalReferenceId(),
                momoDeposit.getDepositorName(),
                merchantDtoService.merchantToDto(momoDeposit.getMerchant()),
                momoDeposit.getNetworkType()
        );
    }

    public AccountResponseDto accountToDto(TAccount account) {

        if (account == null)
            return null;

        return new AccountResponseDto(
                account.getId(),
                account.getCreatedOn(),
                account.getModifiedOn(),
                userDtoService.userToDto(account.getCreatedBy()),
                userDtoService.userToDto(account.getModifiedBy()),
                account.getName(),
                account.getCode(),
                account.getStatus(),
                merchantDtoService.merchantToDto(account.getMerchant()),
                account.getCurrentBalance(),
                account.getAccountType(),
                account.getBalanceToNotifyAt(),
                account.getBalanceNotificationSentOn(),
                account.getActivateOn(),
                userDtoService.userToDto(account.getActivatedBy()),
                account.getSuspendedOn(),
                userDtoService.userToDto(account.getSuspendedBy()),
                account.getClosedOn(),
                userDtoService.userToDto(account.getClosedBy())
        );
    }

    public AccountTransactionResponseDto accountTransactionToDto(TAccountTransaction accountTransaction) {

        if(accountTransaction == null)
            return null;

        return new AccountTransactionResponseDto(
                accountTransaction.getId(),
                accountTransaction.getCreatedOn(),
                accountTransaction.getModifiedOn(),
                accountToDto(accountTransaction.getAccount()),
                accountTransaction.getTransactionType(),
                accountTransaction.getNonReversal(),
                accountTransaction.getTransactionStatus(),
                accountTransaction.getStatusDescription(),
                accountTransaction.getBalanceBefore(),
                accountTransaction.getBalanceAfter(),
                accountTransaction.getExternalTransactionId()
        );
    }

    public CashFlowResponseDto cashFlowToDto(TCashflow cashflow){

        if(cashflow == null)
            return null;

        return new CashFlowResponseDto(
                cashflow.getId(),
                cashflow.getCreatedOn(),
                cashflow.getModifiedOn(),
                cashflow.getExternalReference(),
                cashflow.getAmount(),
                accountTransactionToDto(cashflow.getFromAccountTransaction()),
                accountTransactionToDto(cashflow.getFromAccountTransaction()),
                accountToDto(cashflow.getFromAccount()),
                accountToDto(cashflow.getToAccount()),
                cashflow.getFlowType()
        );
    }
}
