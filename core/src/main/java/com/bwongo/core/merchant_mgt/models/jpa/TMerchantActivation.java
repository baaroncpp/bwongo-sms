package com.bwongo.core.merchant_mgt.models.jpa;

import com.bwongo.core.base.model.enums.ActivationCodeStatusEnum;
import com.bwongo.core.base.model.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/8/24
 * @LocalTime 3:50 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "t_merchant_activation", schema = "core")
public class TMerchantActivation extends BaseEntity {
    private TMerchant merchant;
    private String activationCode;
    private int activationCount;
    private boolean isResend;
    private boolean isActive;
    private ActivationCodeStatusEnum activationCodeStatus;

    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TMerchant getMerchant() {
        return merchant;
    }

    @Column(name = "activation_code")
    public String getActivationCode() {
        return activationCode;
    }

    @Column(name = "activation_count")
    public int getActivationCount() {
        return activationCount;
    }

    @Column(name = "is_resend")
    public boolean isResend() {
        return isResend;
    }

    @Column(name = "is_active")
    public boolean isActive() {
        return isActive;
    }

    @Column(name = "activatioin_code_status")
    @Enumerated(EnumType.STRING)
    public ActivationCodeStatusEnum getActivationCodeStatus() {
        return activationCodeStatus;
    }
}
