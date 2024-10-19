package com.bwongo.commons.models.event;

import com.bwongo.commons.models.dto.NotificationDto;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/9/24
 * @Time 9:06 PM
 **/
@NoArgsConstructor
public class NotificationEvent implements Event{

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private NotificationDto notificationDto;

    public NotificationEvent(NotificationDto notificationDto) {
        this.notificationDto = notificationDto;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }

    public NotificationDto getNotificationDto() {
        return notificationDto;
    }
}
