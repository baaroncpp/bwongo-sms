package com.bwongo.core.sms_mgt.service;

import com.bwongo.commons.utils.Validate;
import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.account_mgt.repository.TAccountRepository;
import com.bwongo.core.base.model.enums.SmsStatusEnum;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantSmsSetting;
import com.bwongo.core.merchant_mgt.repository.TMerchantRepository;
import com.bwongo.core.merchant_mgt.repository.TMerchantSmsSettingRepository;
import com.bwongo.core.sms_mgt.models.dto.request.SmsRequestDto;
import com.bwongo.core.sms_mgt.models.dto.response.SmsResponseDto;
import com.bwongo.core.sms_mgt.repository.TSmsRepository;
import com.bwongo.core.sms_mgt.service.dto.SmsDtoService;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bwongo.core.account_mgt.utils.AccountUtils.checkIfAccountIsValid;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.MERCHANT_NOT_FOUND;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.MERCHANT_SMS_SETTING_NOT_FOUND;
import static com.bwongo.core.sms_mgt.utils.SmsUtils.getInternalReference;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 9:16 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final TSmsRepository smsRepository;
    private final SmsDtoService smsDtoService;
    private final AuditService auditService;
    private final TUserRepository userRepository;
    private final TMerchantRepository merchantRepository;
    private final TAccountRepository accountRepository;
    private final TMerchantSmsSettingRepository merchantSmsSettingRepository;

    public SmsResponseDto sendSms(SmsRequestDto smsRequestDto) {

        smsRequestDto.validate();
        var merchant = getLoggedInUserMerchant();
        var account = getMerchantAccount();
        var smsCost = getMerchantSmsSetting().getSmsCost();

        checkIfAccountIsValid.accept(account, smsCost);

        var sms = smsDtoService.dtoToSms(smsRequestDto);
        sms.setMerchant(merchant);
        sms.setSmsStatus(SmsStatusEnum.PENDING);
        sms.setResend(Boolean.FALSE);
        sms.setInternalReference(getInternalReference.apply(merchant.getMerchantCode()));
        sms.setResendCount(0);

        auditService.stampAuditedEntity(sms);

        return smsDtoService.smsToDto(smsRepository.save(sms));
    }

    private TMerchantSmsSetting getMerchantSmsSetting() {
        var merchant = getLoggedInUserMerchant();
        var existingSmsSettings = merchantSmsSettingRepository.findByMerchant(merchant);
        Validate.isPresent(existingSmsSettings, MERCHANT_SMS_SETTING_NOT_FOUND, merchant.getId());
        return existingSmsSettings.get();
    }

    private TAccount getMerchantAccount(){
        var merchant = getLoggedInUserMerchant();
        var existingAccount = accountRepository.findByMerchant(merchant);
        Validate.isPresent(existingAccount, MERCHANT_NOT_FOUND, merchant.getId());
        return existingAccount.get();
    }

    private TMerchant getLoggedInUserMerchant() {
        return merchantRepository.findById(getLoggedInUser().getId()).get();
    }

    private TUser getLoggedInUser() {
        var loggedInUserId = auditService.getLoggedInUser().getId();
        return userRepository.findById(loggedInUserId).orElse(null);
    }
}
