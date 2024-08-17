package com.bwongo.core.merchant_mgt.service;

import com.bwongo.commons.exceptions.InsufficientAuthenticationException;
import com.bwongo.commons.utils.DateTimeUtil;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantApiSettingRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantApiSettingResponseDto;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantApiSetting;
import com.bwongo.core.merchant_mgt.repository.TMerchantApiSettingRepository;
import com.bwongo.core.merchant_mgt.service.dto.MerchantDtoService;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

import static com.bwongo.commons.utils.DateTimeUtil.getCurrentUTCTime;
import static com.bwongo.core.merchant_mgt.utils.MerchantUtils.generateApiSecretAndSecret;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 7:22AM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantApiService {

    @Value("${api.encryption-password}")
    private String encryptionPassword;

    private final TMerchantApiSettingRepository merchantApiSettingRepository;
    private final MerchantDtoService merchantDtoService;
    private final AuditService auditService;
    private final TUserRepository userRepository;

    public MerchantApiSettingResponseDto getMerchantApiSetting(MerchantApiSettingRequestDto merchantApiSettingRequestDto) {

        merchantApiSettingRequestDto.validate();
        var merchant = getLoggedInUserMerchant();
        var expiryDate = Instant.now().plus(Duration.ofDays(merchantApiSettingRequestDto.numberOfDays()));

        var apiKeyAndSecretDto = generateApiSecretAndSecret(merchant, encryptionPassword);

        var merchantApiSetting = TMerchantApiSetting.builder()
                .merchant(merchant)
                .apiSecret(apiKeyAndSecretDto.getSecret())
                .keyExpiryDate(expiryDate)
                .isKeyIssued(Boolean.TRUE)
                .apiKey(apiKeyAndSecretDto.getApiKey())
                .isCredentialExpired(Boolean.FALSE)
                .build();

        auditService.stampAuditedEntity(merchantApiSetting);
        return merchantDtoService.merchantApiSettingToDto(merchantApiSettingRepository.save(merchantApiSetting));
    }

    public void invalidateExpiredCredentials(){
        var credentials = merchantApiSettingRepository.findAllByCredentialExpired(Boolean.FALSE);

        credentials.forEach(credential -> {
            try {
                if (credential.getKeyExpiryDate().compareTo(Instant.now()) < 0) {
                    credential.setCredentialExpired(Boolean.TRUE);
                    auditService.stampAuditedEntity(credential);
                    merchantApiSettingRepository.save(credential);
                }
            }catch(Exception e){
                log.error(e.getMessage());
            }
        });

    }

    private TMerchant getLoggedInUserMerchant(){
        var userId = auditService.getLoggedInUser().getId();
        var existingUser = userRepository.findById(userId);
        return existingUser.get().getMerchant();
    }
}
