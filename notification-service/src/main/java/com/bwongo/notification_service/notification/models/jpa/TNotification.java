package com.bwongo.notification_service.notification.models.jpa;

import com.bwongo.commons.models.dto.NotificationStatusEnum;
import com.bwongo.commons.models.dto.NotificationTypeEnum;
import com.bwongo.notification_service.base.models.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/11/24
 * @Time 1:17 PM
 **/
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
