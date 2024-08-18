package com.bwongo.core.merchant_mgt.models.dto.response;

import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;

import java.time.Instant;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 7:24AM
 **/
public record MerchantApiSettingResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        MerchantResponseDto merchant,
        String apiKey,
        String apiSecret,
        boolean isKeyIssued,
        Instant keyExpiryDate
) {
}
