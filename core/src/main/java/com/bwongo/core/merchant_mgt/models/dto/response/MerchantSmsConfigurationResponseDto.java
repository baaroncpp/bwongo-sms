package com.bwongo.core.merchant_mgt.models.dto.response;

import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 2:20 PM
 **/
public record MerchantSmsConfigurationResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        String customizedTitle,
        MerchantResponseDto merchant,
        BigDecimal smsCost,
        int maxNumberOfCharactersPerSms,
        boolean isCustomized,
        String apiKey,
        String apiSecret,
        boolean isKeyIssued,
        Instant keyExpiryDate
) {
}
