package com.bwongo.commons.models.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/11/24
 * @Time 11:17 AM
 **/
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUpdateDto implements Serializable {
    private String internalReference;
    private NotificationStatusEnum notificationStatus;
}
