package com.bwongo.commons.models.event;

import com.bwongo.commons.models.dto.NotificationDto;
import com.bwongo.commons.models.dto.NotificationUpdateDto;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/11/24
 * @Time 11:16 AM
 **/
@ToString
@NoArgsConstructor
public class NotificationUpdateEvent  implements Event{

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private NotificationUpdateDto notificationUpdateDto;


    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }

    public NotificationUpdateEvent(NotificationUpdateDto notificationUpdateDto) {
        this.notificationUpdateDto = notificationUpdateDto;
    }
}
