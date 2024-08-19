package com.bwongo.core.account_mgt.models.jpa;

import com.bwongo.core.base.model.enums.NetworkTypeEnum;
import com.bwongo.core.base.model.enums.TransactionStatusEnum;
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
 * @Date 8/19/24
 * @LocalTime 2:35 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "t_momo_deposit", schema = "core")
public class TMomoDeposit extends AuditEntity {
    private BigDecimal amountDeposit;
    private TransactionStatusEnum transactionStatus;
    private String msisdn;
    private String externalReferenceId;
    private String depositorName;
    private TMerchant merchant;
    private NetworkTypeEnum networkType;

    @Column(name = "amount_deposit")
    public BigDecimal getAmountDeposit() {
        return amountDeposit;
    }

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    public TransactionStatusEnum getTransactionStatus() {
        return transactionStatus;
    }

    @Column(name = "msisdn")
    public String getMsisdn() {
        return msisdn;
    }

    @Column(name = "external_reference_id")
    public String getExternalReferenceId() {
        return externalReferenceId;
    }

    @Column(name = "depositor_name")
    public String getDepositorName() {
        return depositorName;
    }

    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TMerchant getMerchant() {
        return merchant;
    }

    @Column(name = "network_type")
    @Enumerated(EnumType.STRING)
    public NetworkTypeEnum getNetworkType() {
        return networkType;
    }
}
