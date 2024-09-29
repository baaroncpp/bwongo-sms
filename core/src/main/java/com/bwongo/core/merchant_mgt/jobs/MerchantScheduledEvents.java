package com.bwongo.core.merchant_mgt.jobs;

import com.bwongo.core.merchant_mgt.service.MerchantApiService;
import com.bwongo.core.merchant_mgt.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/8/24
 * @LocalTime 10:10 PM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
@Configuration
public class MerchantScheduledEvents {

    private final MerchantService merchantService;
    private final MerchantApiService merchantApiService;

    @Scheduled(fixedDelay = 60000, initialDelay = 30000)
    public void invalidateExpiredActivationCodes(){
        try {
            log.info("Invalidating expired activation codes");
            merchantService.invalidateExpiredActivationCodes();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 100000)
    public void invalidateExpiredApiCredentials(){
        try {
            log.info("Invalidating expired api credentials");
            merchantApiService.invalidateExpiredCredentials();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
