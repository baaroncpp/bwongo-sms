package com.bwongo.notification_service.base.models.dto;


import com.bwongo.notification_service.base.models.enums.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/29/24
 * @LocalTime 1:25 PM
 **/
@Data
@Builder
@ToString
public class NotificationDto implements Serializable {
    private String sender;
    private String recipient;
    private String message;
    private NotificationStatusEnum status;
    private String merchantCode;
    private NotificationTypeEnum notificationType;
    private String internalReference;
    private String externalReference;
}
