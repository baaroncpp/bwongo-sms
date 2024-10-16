package com.bwongo.core.base.jobs;

import com.bwongo.core.base.service.OutBoxEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/16/24
 * @Time 12:38 PM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class OutBoxScheduledEvents {

    private final OutBoxEventService outBoxEventService;

    @Scheduled(fixedDelay = 5000)
    public void pollingAndPublishingOutBoxEvents(){
        try {
            log.info("pulling and publishing outbox events");
            outBoxEventService.outBoxEventPublisher();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
