package com.bwongo.notification_service.base.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.commons.models.dto.NotificationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/30/24
 * @LocalTime 7:41 PM
 **/
@Service
@Slf4j
public class KafkaMessageSubscriber {

    @RetryableTopic(attempts = "4")
    @KafkaListener(topics = "${app.topic.sms}", groupId = "sms-group")
    public void consumeNotification(NotificationDto notification,
                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                    @Header(KafkaHeaders.OFFSET) Long offset) {
        try{
            log.info("Received notification: [ {} ] from {} offset {}", new ObjectMapper().writeValueAsString(notification), topic, offset);

            var condition = (notification.getMerchantCode().isEmpty() || notification.getInternalReference().isEmpty()) ?  Boolean.FALSE : Boolean.TRUE;
            Validate.isTrue(condition, ExceptionType.BAD_REQUEST, "Invalid notification");

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @DltHandler
    public void listenDlt(NotificationDto notification,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) Long offset){
        log.info("Dlt received notification: [ {} ] from {} offset {}", notification.toString(), topic, offset);

    }
}
