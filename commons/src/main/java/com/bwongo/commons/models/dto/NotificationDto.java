package com.bwongo.commons.models.dto;


import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
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
