package com.bwongo.notification_service.base.models.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;


/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/30/24
 * @LocalTime 10:27 PM
 **/
public class NotificationDtoSerializer implements Serializer<NotificationDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, NotificationDto notificationDto) {
        try{
            return objectMapper.writeValueAsBytes(notificationDto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
