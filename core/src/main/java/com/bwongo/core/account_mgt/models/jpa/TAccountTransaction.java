package com.bwongo.core.account_mgt.models.jpa;

import com.bwongo.core.base.model.enums.TransactionStatusEnum;
import com.bwongo.core.base.model.enums.TransactionTypeEnum;
import com.bwongo.core.base.model.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 1:46 PM
 **/
@Setter
@Entity
@Table(name = "t_account_transaction",schema = "core")
public class TAccountTransaction extends BaseEntity {
    private TAccount account;
    private TransactionTypeEnum transactionType;
    private Boolean nonReversal;
    private TransactionStatusEnum transactionStatus;
    private String statusDescription;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String externalTransactionId;

    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TAccount getAccount() {
        return account;
    }

    @Column(name = "transaction_type")
    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    @Column(name = "non_reversal")
    public Boolean getNonReversal() {
        return nonReversal;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public TransactionStatusEnum getTransactionStatus() {
        return transactionStatus;
    }

    @Column(name = "status_description")
    public String getStatusDescription() {
        return statusDescription;
    }

    @Column(name = "balance_before")
    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    @Column(name = "balance_after")
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    @Column(name = "external_transaction_id")
    public String getExternalTransactionId() {
        return externalTransactionId;
    }
}
