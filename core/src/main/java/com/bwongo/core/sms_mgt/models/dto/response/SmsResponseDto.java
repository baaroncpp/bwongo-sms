package com.bwongo.core.sms_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.SmsStatusEnum;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 9:13 PM
 **/
public record SmsResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        String phoneNumber,
        String message,
        String sender,
        SmsStatusEnum smsStatus,
        boolean isResend,
        int resendCount,
        MerchantResponseDto merchant
) {
}
