package com.bwongo.core.sms_mgt.service.dto;

import com.bwongo.core.merchant_mgt.service.dto.MerchantDtoService;
import com.bwongo.core.sms_mgt.models.dto.request.SmsRequestDto;
import com.bwongo.core.sms_mgt.models.dto.response.SmsResponseDto;
import com.bwongo.core.sms_mgt.models.jpa.TSms;
import com.bwongo.core.user_mgt.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 9:13PM
 **/
@Service
@RequiredArgsConstructor
public class SmsDtoService {

    private final UserDtoService userDtoService;
    private final MerchantDtoService merchantDtoService;

    public TSms dtoToSms(SmsRequestDto smsRequestDto){
        if(smsRequestDto == null)
            return null;

        return TSms.builder()
                .sender(smsRequestDto.sender())
                .message(smsRequestDto.message())
                .phoneNumber(smsRequestDto.phoneNumber())
                .build();
    }

    public SmsResponseDto smsToDto(TSms sms){

        if(sms == null)
            return null;

        return new SmsResponseDto(
                sms.getId(),
                sms.getCreatedOn(),
                sms.getModifiedOn(),
                userDtoService.userToDto(sms.getCreatedBy() ),
                userDtoService.userToDto(sms.getModifiedBy()),
                sms.getPhoneNumber(),
                sms.getMessage(),
                sms.getSender(),
                sms.getSmsStatus(),
                sms.isResend(),
                sms.getResendCount(),
                merchantDtoService.merchantToDto(sms.getMerchant())
        );
    }
}
