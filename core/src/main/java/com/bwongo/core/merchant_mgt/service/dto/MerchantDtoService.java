package com.bwongo.core.merchant_mgt.service.dto;

import com.bwongo.core.base.model.enums.MerchantTypeEnum;
import com.bwongo.core.base.service.dto.BaseDtoService;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantSmsConfigurationRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantSmsConfigurationResponseDto;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantSmsConfiguration;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import com.bwongo.core.user_mgt.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 2:23 PM
 **/
@RequiredArgsConstructor
@Service
public class MerchantDtoService {

    private final UserDtoService userDtoService;
    private final BaseDtoService baseDtoService;

    @Value("${sms.default-title}")
    private String defaultTitle;

    public TMerchant dtoToMerchant(MerchantRequestDto merchantRequestDto) {

        if (merchantRequestDto == null)
            return null;

        return TMerchant.builder()
                .email(merchantRequestDto.email())
                .phoneNumber(merchantRequestDto.phoneNumber())
                .merchantName(merchantRequestDto.merchantName())
                .merchantType(MerchantTypeEnum.valueOf(merchantRequestDto.merchantType()))
                .location(merchantRequestDto.location())
                .build();
    }

    public MerchantResponseDto merchantToDto(TMerchant merchant) {

        if (merchant == null)
            return null;

        return new MerchantResponseDto(
                merchant.getId(),
                merchant.getCreatedOn(),
                merchant.getModifiedOn(),
                userDtoService.userToDto(merchant.getCreatedBy()),
                userDtoService.userToDto(merchant.getModifiedBy()),
                baseDtoService.countryToDto(merchant.getCountry()),
                merchant.getEmail(),
                merchant.getPhoneNumber(),
                merchant.getMerchantName(),
                merchant.getMerchantCode(),
                merchant.getLocation(),
                merchant.isActive(),
                merchant.getMerchantType(),
                userDtoService.userToDto(merchant.getActivatedBy())
        );
    }

    public TMerchantSmsConfiguration dtoToMerchantSmsConfiguration(MerchantSmsConfigurationRequestDto merchantSmsConfigurationRequestDto) {

        if(merchantSmsConfigurationRequestDto == null)
            return null;

        var customizedTitle = merchantSmsConfigurationRequestDto.isCustomized() ? merchantSmsConfigurationRequestDto.customizedTitle() : defaultTitle;

        return TMerchantSmsConfiguration.builder()
                .smsCost(merchantSmsConfigurationRequestDto.smsCost())
                .isCustomized(merchantSmsConfigurationRequestDto.isCustomized())
                .customizedTitle(customizedTitle)
                .build();
    }

    public MerchantSmsConfigurationResponseDto merchantSmsConfigurationToDto(TMerchantSmsConfiguration merchantSmsConfiguration){

        if (merchantSmsConfiguration == null)
            return null;

        return new MerchantSmsConfigurationResponseDto(
                merchantSmsConfiguration.getId(),
                merchantSmsConfiguration.getCreatedOn(),
                merchantSmsConfiguration.getModifiedOn(),
                userDtoService.userToDto(merchantSmsConfiguration.getCreatedBy()),
                userDtoService.userToDto(merchantSmsConfiguration.getModifiedBy()),
                merchantSmsConfiguration.getCustomizedTitle(),
                merchantToDto(merchantSmsConfiguration.getMerchant()),
                merchantSmsConfiguration.getSmsCost(),
                merchantSmsConfiguration.getMaxNumberOfCharactersPerSms(),
                merchantSmsConfiguration.isCustomized(),
                merchantSmsConfiguration.getApiKey(),
                merchantSmsConfiguration.getApiSecret(),
                merchantSmsConfiguration.isKeyIssued(),
                merchantSmsConfiguration.getKeyExpiryDate()
        );
    }

    public TUser merchantDtoToUser(MerchantRequestDto merchantRequestDto){

        if(merchantRequestDto.merchantUser() == null)
            return null;

        return userDtoService.dtoToTUser(merchantRequestDto.merchantUser());
    }
}
