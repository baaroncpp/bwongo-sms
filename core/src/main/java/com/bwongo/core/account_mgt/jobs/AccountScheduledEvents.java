package com.bwongo.core.account_mgt.jobs;

import com.bwongo.core.account_mgt.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/21/24
 * @LocalTime 12:53 PM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountScheduledEvents {

    private final AccountService accountService;

    @Scheduled(fixedDelay = 90000, initialDelay = 30000)
    public void updatePendingMomoDeposits(){
        try {
            log.info("updating MOMO pending deposits");
            accountService.updateMomoPendingDeposits();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
