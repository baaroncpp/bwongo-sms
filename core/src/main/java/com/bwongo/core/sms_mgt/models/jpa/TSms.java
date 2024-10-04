package com.bwongo.core.sms_mgt.models.jpa;

import com.bwongo.core.base.model.enums.PaymentStatusEnum;
import com.bwongo.core.base.model.enums.SmsStatusEnum;
import com.bwongo.core.base.model.jpa.AuditEntity;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 8:43 PM
 **/
@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_sms", schema = "core")
public class TSms extends AuditEntity {
    private String phoneNumber;
    private String message;
    private String sender;
    private SmsStatusEnum smsStatus;
    private boolean isResend;
    private int resendCount;
    private TMerchant merchant;
    private String internalReference;
    private String externalReference;
    private PaymentStatusEnum paymentStatus;
    private BigDecimal cost;

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    @Column(name = "sender")
    public String getSender() {
        return sender;
    }

    @Column(name = "sms_status")
    @Enumerated(EnumType.STRING)
    public SmsStatusEnum getSmsStatus() {
        return smsStatus;
    }

    @Column(name = "is_resend")
    public boolean isResend() {
        return isResend;
    }

    @Column(name = "resend_count")
    public int getResendCount() {
        return resendCount;
    }

    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TMerchant getMerchant() {
        return merchant;
    }

    @Column(name = "internal_reference")
    public String getInternalReference() {
        return internalReference;
    }

    @Column(name = "external_reference")
    public String getExternalReference() {
        return externalReference;
    }

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    @Column(name = "cost")
    public BigDecimal getCost() {
        return cost;
    }
}
