package com.bwongo.core.merchant_mgt.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.enums.ActivationCodeStatusEnum;
import com.bwongo.core.base.model.enums.MerchantStatusEnum;
import com.bwongo.core.base.repository.TAddressRepository;
import com.bwongo.core.base.repository.TCountryRepository;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.base.service.BaseService;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantActivation;
import com.bwongo.core.merchant_mgt.repository.TMerchantActivationRepository;
import com.bwongo.core.merchant_mgt.repository.TMerchantRepository;
import com.bwongo.core.merchant_mgt.repository.TMerchantSmsConfigurationRepository;
import com.bwongo.core.merchant_mgt.service.dto.MerchantDtoService;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bwongo.commons.text.StringUtil.getRandom6DigitString;
import static com.bwongo.commons.utils.DateTimeUtil.getCurrentUTCTime;
import static com.bwongo.core.base.utils.BaseMsgUtils.*;
import static com.bwongo.core.merchant_mgt.utils.MessageMsgConstants.*;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 2:23 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {

    private final AuditService auditService;
    private final MerchantDtoService merchantDtoService;
    private final BaseService baseService;
    private final TMerchantRepository merchantRepository;
    private final TMerchantSmsConfigurationRepository merchantSmsConfigurationRepository;
    private final TUserRepository userRepository;
    private final TCountryRepository countryRepository;
    private final TAddressRepository addressRepository;
    private final TMerchantActivationRepository merchantActivationRepository;

    public MerchantResponseDto addMerchant(MerchantRequestDto merchantRequestDto){

        merchantRequestDto.validate();

        var merchantMail = merchantRequestDto.email();
        var merchantPhoneNumber = merchantRequestDto.phoneNumber();
        var merchantCode = generateMerchantCode();
        var countryId = merchantRequestDto.countryId();

        Validate.isTrue(!merchantRepository.existsByEmail(merchantMail), ExceptionType.BAD_REQUEST, EMAIL_ALREADY_TAKEN, merchantMail);
        Validate.isTrue(!merchantRepository.existsByPhoneNumber(merchantPhoneNumber), ExceptionType.BAD_REQUEST, PHONE_NUMBER_ALREADY_TAKEN, merchantPhoneNumber);

        var existingCountry = countryRepository.findById(countryId);
        Validate.isPresent(existingCountry, COUNTRY_WITH_ID_NOT_FOUND);
        var country = existingCountry.get();

        var merchant = merchantDtoService.dtoToMerchant(merchantRequestDto);
        merchant.setActive(Boolean.FALSE);
        merchant.setMerchantCode(merchantCode);
        merchant.setCountry(country);
        auditService.stampAuditedEntity(merchant);
        var savedMerchant = merchantRepository.save(merchant);

        var user = merchantDtoService.merchantDtoToUser(merchantRequestDto);

        Validate.isTrue(!userRepository.existsByEmail(user.getEmail()), ExceptionType.BAD_REQUEST, EMAIL_ALREADY_TAKEN, user.getEmail());

        user.setAccountExpired(Boolean.FALSE);
        user.setAccountLocked(Boolean.FALSE);
        user.setCredentialExpired(Boolean.FALSE);
        user.setApproved(Boolean.FALSE);
        user.setMerchant(savedMerchant);
        auditService.stampLongEntity(user);

        userRepository.save(user);

        sendActivationCode(savedMerchant);

        return merchantDtoService.merchantToDto(savedMerchant);
    }

    public void resendActivationCode(Long merchantId){

        var merchant = getMerchantById(merchantId);

        var merchantActivations = merchantActivationRepository.findAllByMerchantAndActivationCodeStatus(merchant, ActivationCodeStatusEnum.FAILED);
        Validate.isTrue(merchantActivations.size() >= 3, ExceptionType.BAD_REQUEST, CONTACT_ADMIN_MAX_FAILS);

        var existingMerchantActivation = merchantActivationRepository.findByMerchantAndActive(merchant, Boolean.TRUE);

        if(existingMerchantActivation.isPresent()){
            var merchantActivation = existingMerchantActivation.get();
            merchantActivation.setActivationCodeStatus(ActivationCodeStatusEnum.CANCELLED);
            merchantActivation.setActive(Boolean.FALSE);

            auditService.stampLongEntity(merchantActivation);
            merchantActivationRepository.save(merchantActivation);
        }

        var isResend = merchantActivations.isEmpty() ? Boolean.FALSE : Boolean.TRUE;

        var activationCode = generateMerchantCode();
        var merchantActivation = TMerchantActivation.builder()
                .merchant(merchant)
                .activationCode(activationCode)
                .isResend(isResend)
                .isActive(Boolean.TRUE)
                .activationCodeStatus(ActivationCodeStatusEnum.ACTIVE)
                .build();

        auditService.stampLongEntity(merchantActivation);
        merchantActivationRepository.save(merchantActivation);

        //TODO SEND TO KAFKA THE ACTIVATION CODE
    }

    @Transactional
    public MerchantResponseDto activateMerchantByCode(String code){

        var existingMerchantActivation = merchantActivationRepository.findByActivationCode(code);
        Validate.isPresent(existingMerchantActivation, MERCHANT_ACTIVATION_NOT_FOUND, code);
        var merchantActivation = existingMerchantActivation.get();
        var merchant = merchantActivation.getMerchant();

        Validate.isTrue(merchantActivation.isActive(), ExceptionType.BAD_REQUEST, INVALID_ACTIVATION_CODE, merchantActivation.getActivationCodeStatus());

        merchantActivation.setActive(Boolean.FALSE);
        merchantActivation.setActivationCodeStatus(ActivationCodeStatusEnum.USED);

        auditService.stampLongEntity(merchantActivation);
        merchantActivationRepository.save(merchantActivation);

        merchant.setActive(Boolean.TRUE);
        merchant.setActivatedBy(getLoggedInUser());
        merchant.setMerchantStatus(MerchantStatusEnum.ACTIVE);

        auditService.stampAuditedEntity(merchant);
        var activatedMerchant = merchantRepository.save(merchant);

        return merchantDtoService.merchantToDto(activatedMerchant);
    }

    public MerchantResponseDto activateMerchantByAdmin(Long merchantId){

        var merchant = getMerchantById(merchantId);
        Validate.isTrue(!merchant.isActive(), ExceptionType.BAD_REQUEST, MERCHANT_ALREADY_ACTIVATED, merchantId);

        merchant.setActive(Boolean.TRUE);
        merchant.setActivatedBy(getLoggedInUser());
        merchant.setMerchantStatus(MerchantStatusEnum.ACTIVE);

        auditService.stampAuditedEntity(merchant);
        var activatedMerchant = merchantRepository.save(merchant);

        //Invalidate all activation codes
        var merchantActivations = merchantActivationRepository.findAllByMerchantAndActivationCodeStatus(merchant, ActivationCodeStatusEnum.ACTIVE);

        merchantActivations.forEach(activation -> {
            activation.setActive(Boolean.FALSE);
            activation.setActivationCodeStatus(ActivationCodeStatusEnum.CANCELLED);

            auditService.stampLongEntity(activation);
            merchantActivationRepository.save(activation);
        });

        return merchantDtoService.merchantToDto(activatedMerchant);
    }

    @Transactional
    protected void sendActivationCode(TMerchant merchant){

        Validate.isTrue(!merchant.isActive(), ExceptionType.BAD_REQUEST, MERCHANT_ALREADY_ACTIVATED, merchant.getId());

        var merchantActivations = merchantActivationRepository.findAllByMerchantAndActivationCodeStatus(merchant, ActivationCodeStatusEnum.FAILED);
        Validate.isTrue(merchantActivations.size() >= 3, ExceptionType.BAD_REQUEST, CONTACT_ADMIN_MAX_FAILS);

        var activationCode = generateMerchantCode();
        var isResend = merchantActivations.isEmpty() ? Boolean.FALSE : Boolean.TRUE;

        var merchantActivation = TMerchantActivation.builder()
                .merchant(merchant)
                .activationCode(activationCode)
                .isResend(isResend)
                .isActive(Boolean.TRUE)
                .activationCodeStatus(ActivationCodeStatusEnum.ACTIVE)
                .build();

        auditService.stampLongEntity(merchantActivation);
        merchantActivationRepository.save(merchantActivation);

        //TODO SEND TO KAFKA THE ACTIVATION CODE
    }

    private String generateMerchantCode(){
        var code = "";
        do{
            code = getRandom6DigitString();
        }while(merchantActivationRepository.findByActivationCode(code).isPresent());
        return code;
    }

    public void invalidateExpiredActivationCodes(){
        var merchantActivations = merchantActivationRepository.findAllByCreatedOnBeforeAndActive(getCurrentUTCTime(), Boolean.TRUE);

        merchantActivations.forEach(activation -> {
            activation.setActive(Boolean.FALSE);
            activation.setActivationCodeStatus(ActivationCodeStatusEnum.EXPIRED);

            auditService.stampLongEntity(activation);
            merchantActivationRepository.save(activation);
        });
    }

    private TMerchant getMerchantById(Long id){
        var existingMerchant = merchantRepository.findById(id);
        Validate.isPresent(existingMerchant, MERCHANT_NOT_FOUND, id);
        return existingMerchant.get();
    }

    private TUser getLoggedInUser(){
        var userId = auditService.getLoggedInUser().getId();
        return userRepository.findById(userId).get();
    }
}
