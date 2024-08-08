package com.bwongo.core.merchant_mgt.models.dto.response;

import com.bwongo.core.base.model.dto.response.CountryResponseDto;
import com.bwongo.core.base.model.enums.MerchantTypeEnum;
import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 2:16 PM
 **/
public record MerchantResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        CountryResponseDto country,
        String email,
        String phoneNumber,
        String merchantName,
        String merchantCode,
        String location,
        boolean isActive,
        MerchantTypeEnum merchantType,
        UserResponseDto activatedBy
) {
}
