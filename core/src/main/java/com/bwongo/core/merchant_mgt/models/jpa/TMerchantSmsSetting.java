package com.bwongo.core.merchant_mgt.models.jpa;

import com.bwongo.core.base.model.enums.PaymentTypeEnum;
import com.bwongo.core.base.model.jpa.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/28/24
 * @LocalTime 3:59 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "t_merchant_sms_setting", schema = "core")
public class TMerchantSmsSetting extends AuditEntity {
    private String customizedTitle;
    private TMerchant merchant;
    private BigDecimal smsCost;
    private int maxNumberOfCharactersPerSms;
    private boolean isCustomized;
    private PaymentTypeEnum paymentType;

    @Column(name = "customized_title")
    public String getCustomizedTitle() {
        return customizedTitle;
    }

    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TMerchant getMerchant() {
        return merchant;
    }

    @Column(name = "sms_cost")
    public BigDecimal getSmsCost() {
        return smsCost;
    }

    @Column(name = "max_number_of_characters_per_sms")
    public int getMaxNumberOfCharactersPerSms() {
        return maxNumberOfCharactersPerSms;
    }

    @Column(name = "is_customized")
    public boolean isCustomized() {
        return isCustomized;
    }

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    public PaymentTypeEnum getPaymentType() {
        return paymentType;
    }
}
