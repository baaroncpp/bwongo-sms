package com.bwongo.notification_service.base.service;

import com.bwongo.commons.exceptions.BadRequestException;
import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.models.event.NotificationEvent;
import com.bwongo.commons.utils.Validate;
import com.bwongo.commons.models.dto.NotificationDto;
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
 * @Date 8/30/24
 * @LocalTime 7:41PM
 **/
@Service
@Slf4j
public class KafkaMessageSubscriber {

    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000), exclude = {NullPointerException.class, BadRequestException.class})
    @KafkaListener(topics = "${app.topic.sms}", groupId = "sms-group")
    public void consumeNotification(NotificationEvent notificationEvent,
                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                    @Header(KafkaHeaders.OFFSET) Long offset) {
        try{
            log.info("Received notification: [ {} ] from {} offset {}", new ObjectMapper().writeValueAsString(notificationEvent), topic, offset);

            var condition = notificationEvent.getEventId().toString().isEmpty() ?  Boolean.FALSE : Boolean.TRUE;
            Validate.isTrue(condition, ExceptionType.BAD_REQUEST, "Invalid notification");

            //TODO after receiving
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @DltHandler
    public void listenDlt(NotificationEvent notificationEvent,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) Long offset){
        log.info("Dlt received notification: [ {} ] from {} offset {}", notificationEvent.toString(), topic, offset);

    }
}
