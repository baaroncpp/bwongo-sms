package com.bwongo.core.account_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.CashFlowEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 8:04 PM
 **/
public record CashFlowResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String externalReference,
        BigDecimal amount,
        AccountTransactionResponseDto fromAccountTransaction,
        AccountTransactionResponseDto toAccountTransaction,
        AccountResponseDto fromAccount,
        AccountResponseDto toAccount,
        CashFlowEnum flowType
) {
}
