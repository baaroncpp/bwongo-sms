package com.bwongo.core.merchant_mgt.service.dto;

import com.bwongo.core.base.model.enums.MerchantTypeEnum;
import com.bwongo.core.base.service.dto.BaseDtoService;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantSmsSettingRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantUpdateRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantApiSettingResponseDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantSmsSettingResponseDto;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantApiSetting;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantSmsSetting;
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

    public TMerchant updateDtoToMerchant(MerchantUpdateRequestDto merchantUpdateRequestDto) {

        if (merchantUpdateRequestDto == null)
            return null;

        return TMerchant.builder()
                .email(merchantUpdateRequestDto.email())
                .phoneNumber(merchantUpdateRequestDto.phoneNumber())
                .merchantName(merchantUpdateRequestDto.merchantName())
                .merchantType(MerchantTypeEnum.valueOf(merchantUpdateRequestDto.merchantType()))
                .location(merchantUpdateRequestDto.location())
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

    public TMerchantSmsSetting dtoToMerchantSmsSetting(MerchantSmsSettingRequestDto merchantSmsSettingRequestDto) {

        if(merchantSmsSettingRequestDto == null)
            return null;

        var customizedTitle = merchantSmsSettingRequestDto.isCustomized() ? merchantSmsSettingRequestDto.customizedTitle() : defaultTitle;

        return TMerchantSmsSetting.builder()
                //.smsCost(merchantSmsSettingRequestDto.smsCost())
                .isCustomized(merchantSmsSettingRequestDto.isCustomized())
                .customizedTitle(customizedTitle)
                .build();
    }

    public MerchantSmsSettingResponseDto merchantSmsSettingToDto(TMerchantSmsSetting merchantSmsSetting){

        if (merchantSmsSetting == null)
            return null;

        return new MerchantSmsSettingResponseDto(
                merchantSmsSetting.getId(),
                merchantSmsSetting.getCreatedOn(),
                merchantSmsSetting.getModifiedOn(),
                userDtoService.userToDto(merchantSmsSetting.getCreatedBy()),
                userDtoService.userToDto(merchantSmsSetting.getModifiedBy()),
                merchantSmsSetting.getCustomizedTitle(),
                merchantToDto(merchantSmsSetting.getMerchant()),
                merchantSmsSetting.getSmsCost(),
                merchantSmsSetting.getMaxNumberOfCharactersPerSms(),
                merchantSmsSetting.isCustomized()
        );
    }

    public TUser merchantDtoToUser(MerchantRequestDto merchantRequestDto){

        if(merchantRequestDto.merchantUser() == null)
            return null;

        return userDtoService.dtoToMerchantUser(merchantRequestDto.merchantUser());
    }

    public MerchantApiSettingResponseDto merchantApiSettingToDto(TMerchantApiSetting merchantApiSetting){

        if(merchantApiSetting == null)
            return null;

        return new MerchantApiSettingResponseDto(
                merchantApiSetting.getId(),
                merchantApiSetting.getCreatedOn(),
                merchantApiSetting.getModifiedOn(),
                userDtoService.userToDto(merchantApiSetting.getCreatedBy()),
                userDtoService.userToDto(merchantApiSetting.getModifiedBy()),
                merchantToDto(merchantApiSetting.getMerchant()),
                merchantApiSetting.getApiKey(),
                merchantApiSetting.getApiSecret(),
                merchantApiSetting.isKeyIssued(),
                merchantApiSetting.getKeyExpiryDate()
        );
    }
}
