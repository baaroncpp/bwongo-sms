package com.bwongo.core.base.service;

import com.bwongo.commons.models.dto.NotificationDto;
import com.bwongo.commons.models.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/29/24
 * @LocalTime 12:47 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessagePublisher {

    @Value("${app.topic.sms}")
    private String smsTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotificationToTopic(NotificationEvent notificationEvent) {

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(smsTopic, notificationEvent);

        future.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Notification: [ {} ] sent successfully, with offset {}",
                        notificationEvent.toString(),
                        result.getRecordMetadata().offset());
            }else{
                log.error("Error sending notification :[ {} ] to topic due to : ", notificationEvent.toString() ,exception);
            }
        });
        kafkaTemplate.send(smsTopic, notificationEvent);
    }
}
