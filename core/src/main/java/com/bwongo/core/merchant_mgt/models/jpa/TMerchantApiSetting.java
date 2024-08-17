package com.bwongo.core.merchant_mgt.models.jpa;

import com.bwongo.core.base.model.jpa.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 6:41 AM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "t_merchant_api_setting", schema = "core")
public class TMerchantApiSetting extends AuditEntity {
    private TMerchant merchant;
    private String apiKey;
    private String apiSecret;
    private boolean isKeyIssued;
    private boolean isCredentialExpired;
    private Instant keyExpiryDate;

    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TMerchant getMerchant() {
        return merchant;
    }


    @Column(name = "api_key")
    public String getApiKey() {
        return apiKey;
    }

    @Column(name = "api_secret")
    public String getApiSecret() {
        return apiSecret;
    }

    @Column(name = "is_key_issued")
    public boolean isKeyIssued() {
        return isKeyIssued;
    }

    @Column(name = "is_credential_expired")
    public boolean isCredentialExpired() {
        return isCredentialExpired;
    }

    @Column(name = "key_expiry_date")
    public Instant getKeyExpiryDate() {
        return keyExpiryDate;
    }
}
