package com.bwongo.core.base.service;

import com.bwongo.commons.exceptions.BadRequestException;
import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.models.dto.NotificationDto;
import com.bwongo.commons.models.event.NotificationUpdateEvent;
import com.bwongo.commons.utils.Validate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/11/24
 * @Time 11:11 AM
 **/
@Service
@Slf4j
public class KafkerMessageSubscriber {

    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000), exclude = {NullPointerException.class, BadRequestException.class})
    @KafkaListener(topics = "${app.topic.sms-update}", groupId = "sms-update-group")
    public void consumeNotificationUpdate(NotificationUpdateEvent notificationUpdateEvent,
                                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                          @Header(KafkaHeaders.OFFSET) Long offset){
        try{
            log.info("Received notification update: [ {} ] from {} offset {}", new ObjectMapper().writeValueAsString(notificationUpdateEvent), topic, offset);

            var condition = (notificationUpdateEvent.getEventId().toString().isEmpty()) ?  Boolean.FALSE : Boolean.TRUE;
            Validate.isTrue(condition, ExceptionType.BAD_REQUEST, "Invalid notification");

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @DltHandler
    public void listenDlt(NotificationUpdateEvent notificationUpdateEvent,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) Long offset){
        log.info("Dlt received notification: [ {} ] from {} offset {}", notificationUpdateEvent.toString(), topic, offset);

    }
}
