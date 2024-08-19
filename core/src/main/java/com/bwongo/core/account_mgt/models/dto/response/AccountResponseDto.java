package com.bwongo.core.account_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.AccountStatusEnum;
import com.bwongo.core.base.model.enums.AccountTypeEnum;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;
import com.bwongo.core.user_mgt.models.jpa.TUser;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 7:56 PM
 **/
public record AccountResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        String name,
        String code,
        AccountStatusEnum status,
        MerchantResponseDto merchant,
        BigDecimal currentBalance,
        AccountTypeEnum accountType,
        BigDecimal balanceToNotifyAt,
        Date balanceNotificationSentOn,
        Date activateOn,
        UserResponseDto activatedBy,
        Date suspendedOn,
        UserResponseDto suspendedBy,
        Date closedOn,
        UserResponseDto closedBy
) {
}
