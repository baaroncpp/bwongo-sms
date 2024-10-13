package com.bwongo.core.base.model.jpa;

import com.bwongo.core.base.model.enums.NotificationStatusEnum;
import com.bwongo.core.base.model.enums.NotificationTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/29/24
 * @LocalTime 1:15 PM
 **/
@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_notification", schema = "core")
public class TNotification extends BaseEntity {
    private String sender;
    private String recipient;
    private String message;
    private NotificationStatusEnum status;
    private String merchantCode;
    private NotificationTypeEnum notificationType;
    private String internalReference;
    private String externalReference;
    private String eventId;

    @Column(name = "sender")
    public String getSender() {
        return sender;
    }

    @Column(name = "recipient")
    public String getRecipient() {
        return recipient;
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public NotificationStatusEnum getStatus() {
        return status;
    }

    @Column(name = "merchant_code")
    public String getMerchantCode() {
        return merchantCode;
    }

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    public NotificationTypeEnum getNotificationType() {
        return notificationType;
    }

    @Column(name = "internal_reference")
    public String getInternalReference() {
        return internalReference;
    }

    @Column(name = "external_reference")
    public String getExternalReference() {
        return externalReference;
    }

    @Column(name = "event_id")
    public String getEventId() {
        return eventId;
    }
}
