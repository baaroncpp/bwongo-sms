package com.bwongo.core.merchant_mgt.models.jpa;

import com.bwongo.core.base.model.enums.MerchantStatusEnum;
import com.bwongo.core.base.model.enums.MerchantTypeEnum;
import com.bwongo.core.base.model.jpa.AuditEntity;
import com.bwongo.core.base.model.jpa.TCountry;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/28/24
 * @LocalTime 3:49 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "t_merchant", schema = "core")
public class TMerchant extends AuditEntity {
    private TCountry country;
    private String email;
    private String phoneNumber;
    private String merchantName;
    private String merchantCode;
    private String location;
    private boolean isActive;
    private MerchantTypeEnum merchantType;
    private Boolean nonVerifiedPhoneNumber;
    private TUser activatedBy;
    private MerchantStatusEnum merchantStatus;

    @JoinColumn(name = "country_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TCountry getCountry() {
        return country;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "merchant_name")
    public String getMerchantName() {
        return merchantName;
    }

    @Column(name = "merchant_code")
    public String getMerchantCode() {
        return merchantCode;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    @Column(name = "is_active")
    public boolean isActive() {
        return isActive;
    }

    @Column(name = "merchant_type")
    @Enumerated(EnumType.STRING)
    public MerchantTypeEnum getMerchantType() {
        return merchantType;
    }

    @Column(name = "non_verified_phone_number")
    public Boolean getNonVerifiedPhoneNumber() {
        return nonVerifiedPhoneNumber;
    }

    @JoinColumn(name = "activated_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public TUser getActivatedBy() {
        return activatedBy;
    }

    @Column(name = "merchant_status")
    @Enumerated(EnumType.STRING)
    public MerchantStatusEnum getMerchantStatus() {
        return merchantStatus;
    }
}
