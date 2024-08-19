package com.bwongo.core.account_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.NetworkTypeEnum;
import com.bwongo.core.base.model.enums.TransactionStatusEnum;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 8:02 PM
 **/
public record MomoDepositResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        BigDecimal amountDeposit,
        TransactionStatusEnum transactionStatus,
        String msisdn,
        String externalReferenceId,
        String depositorName,
        MerchantResponseDto merchant,
        NetworkTypeEnum networkType
) {
}
