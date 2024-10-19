package com.bwongo.notification_service.base.service;

import com.bwongo.commons.models.dto.NotificationUpdateDto;
import com.bwongo.commons.models.event.NotificationUpdateEvent;
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
 * @Date 10/11/24
 * @Time 11:05AM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessagePublisher {

    @Value("${app.topic.sms-update}")
    private String notificationUpdateTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotificationUpdate(NotificationUpdateDto notificationUpdateDto){
        var notificationUpdateEvent = new NotificationUpdateEvent(notificationUpdateDto);

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(notificationUpdateTopic, notificationUpdateEvent);

            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    log.info("Notification: [ {} ] sent successfully, with offset {}",
                            notificationUpdateEvent.toString(),
                            result.getRecordMetadata().offset());
                }else{
                    log.error("Error sending notification :[ {} ] to topic due to : ", notificationUpdateEvent.toString() ,exception);
                }
            });
            kafkaTemplate.send(notificationUpdateTopic, notificationUpdateEvent);
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
