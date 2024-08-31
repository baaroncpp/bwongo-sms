package com.bwongo.core.user_mgt.models.jpa;

import com.bwongo.core.base.model.enums.UserTypeEnum;
import com.bwongo.core.base.model.jpa.BaseEntity;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "t_user", schema = "core")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TUser extends BaseEntity {
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private boolean accountLocked;
    private boolean accountExpired;
    private boolean credentialExpired;
    private boolean approved;
    private boolean initialPasswordReset;
    private TUserGroup userGroup;
    private boolean isDeleted;
    private Long approvedBy;
    private UserTypeEnum userType;
    private Boolean nonVerifiedEmail;
    private String imagePath;
    private Long merchantId;

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "second_name")
    public String getSecondName() {
        return secondName;
    }

    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "account_locked")
    public boolean isAccountLocked() {
        return accountLocked;
    }

    @Column(name = "account_expired")
    public boolean isAccountExpired() {
        return accountExpired;
    }

    @Column(name = "cred_expired")
    public boolean isCredentialExpired() {
        return credentialExpired;
    }

    @JoinColumn(name = "user_group_id", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TUserGroup getUserGroup() {
        return userGroup;
    }

    @Column(name = "approved")
    public boolean isApproved() {
        return approved;
    }

    @Column(name = "is_deleted")
    public boolean getDeleted() {
        return isDeleted;
    }

    @Column(name = "approved_by")
    public Long getApprovedBy() {
        return approvedBy;
    }

    @Column(name = "initial_password_reset")
    public boolean isInitialPasswordReset() {
        return initialPasswordReset;
    }

    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    public UserTypeEnum getUserType() {
        return userType;
    }

    @Column(name = "non_verified_email")
    public Boolean getNonVerifiedEmail() {
        return nonVerifiedEmail;
    }

    @Column(name = "image_path")
    public String getImagePath() {
        return imagePath;
    }

    @Column(name = "merchant_id")
    public Long getMerchantId() {
        return merchantId;
    }
}
