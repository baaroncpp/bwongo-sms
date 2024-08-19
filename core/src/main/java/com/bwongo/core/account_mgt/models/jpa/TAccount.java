package com.bwongo.core.account_mgt.models.jpa;

import com.bwongo.core.base.model.enums.AccountStatusEnum;
import com.bwongo.core.base.model.enums.AccountTypeEnum;
import com.bwongo.core.base.model.jpa.AuditEntity;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 12:55 PM
 **/
@Entity
@Setter
@Table(name = "t_account", schema = "core")
public class TAccount extends AuditEntity {
    private String name;
    private String code;
    private AccountStatusEnum status;
    private TMerchant merchant;
    private BigDecimal currentBalance;
    private AccountTypeEnum accountType;
    private BigDecimal balanceToNotifyAt;
    private Date balanceNotificationSentOn;
    private Date activateOn;
    private TUser activatedBy;
    private Date suspendedOn;
    private TUser suspendedBy;
    private Date closedOn;
    private TUser closedBy;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    @Column(name = "status")
    public AccountStatusEnum getStatus() {
        return status;
    }

    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TMerchant getMerchant() {
        return merchant;
    }

    @Column(name = "current_balance")
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    @Column(name = "account_type")
    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    @Column(name = "balance_to_notify_at")
    public BigDecimal getBalanceToNotifyAt() {
        return balanceToNotifyAt;
    }

    @Column(name = "balance_notification_sent_on")
    public Date getBalanceNotificationSentOn() {
        return balanceNotificationSentOn;
    }

    @Column(name = "activated_on")
    public Date getActivateOn() {
        return activateOn;
    }

    @JoinColumn(name = "activated_by",referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getActivatedBy() {
        return activatedBy;
    }

    @Column(name = "suspended_on")
    public Date getSuspendedOn() {
        return suspendedOn;
    }

    @JoinColumn(name = "suspended_by", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getSuspendedBy() {
        return suspendedBy;
    }

    @Column(name = "closed_on")
    public Date getClosedOn() {
        return closedOn;
    }

    @JoinColumn(name = "closed_by", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getClosedBy() {
        return closedBy;
    }
}
