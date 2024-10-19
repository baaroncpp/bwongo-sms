package com.bwongo.commons.models.event;

import java.util.Date;
import java.util.UUID;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/9/24
 * @Time 9:04 PM
 **/
public interface Event {
    UUID getEventId();
    Date getEventDate();
}
