package com.bwongo.core.account_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.TransactionStatusEnum;
import com.bwongo.core.base.model.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 7:59 PM
 **/
public record AccountTransactionResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        AccountResponseDto account,
        TransactionTypeEnum transactionType,
        Boolean nonReversal,
        TransactionStatusEnum transactionStatus,
        String statusDescription,
        BigDecimal balanceBefore,
        BigDecimal balanceAfter,
        String externalTransactionId
) {
}
