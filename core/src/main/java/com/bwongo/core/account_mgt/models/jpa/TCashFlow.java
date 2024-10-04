package com.bwongo.core.account_mgt.models.jpa;

import com.bwongo.core.base.model.enums.CashFlowEnum;
import com.bwongo.core.base.model.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:20PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "t_cash_flow",schema = "core")
public class TCashFlow extends BaseEntity {
    private String externalReference;
    private String internalReference;
    private BigDecimal amount;
    private TAccountTransaction fromAccountTransaction;
    private TAccountTransaction toAccountTransaction;
    private TAccount fromAccount; // Decreasing in value
    private TAccount toAccount; // Increasing in value
    private CashFlowEnum flowType;

    @Column(name = "external_reference")
    public String getExternalReference() {
        return externalReference;
    }

    @Column(name = "internal_reference")
    public String getInternalReference() {
        return internalReference;
    }

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JoinColumn(name = "from_account_transaction_id", referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TAccountTransaction getFromAccountTransaction() {
        return fromAccountTransaction;
    }

    @JoinColumn(name = "to_account_transaction_id", referencedColumnName = "id", insertable = true,updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TAccountTransaction getToAccountTransaction() {
        return toAccountTransaction;
    }

    @JoinColumn(name = "from_account_id",referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TAccount getFromAccount() {
        return fromAccount;
    }

    @JoinColumn(name = "to_account_id",referencedColumnName = "id", insertable = true, updatable = true)
    @OneToOne(fetch = FetchType.LAZY)
    public TAccount getToAccount() {
        return toAccount;
    }

    @Column(name = "flow_type")
    @Enumerated(EnumType.STRING)
    public CashFlowEnum getFlowType() {
        return flowType;
    }
}
