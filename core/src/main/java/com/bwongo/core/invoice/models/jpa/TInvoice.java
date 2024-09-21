package com.bwongo.core.invoice.models.jpa;

import com.bwongo.core.base.model.enums.InvoiceStatusEnum;
import com.bwongo.core.base.model.jpa.BaseEntity;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/20/24
 * @Time 7:51 PM
 **/
@Setter
@Entity
@Table(name = "t_invoice", schema = "core")
public class TInvoice extends BaseEntity {
    private String invoiceNumber;
    private Date issuedOnDate;
    private BigDecimal totalAmount;
    private TMerchant merchant;
    private InvoiceStatusEnum invoiceStatus;
    private Date paymentDueDate;
    private Long quantity;

    @Column(name = "invoice_number")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @Column(name = "issued_on_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getIssuedOnDate() {
        return issuedOnDate;
    }

    @Column(name = "total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.LAZY)
    public TMerchant getMerchant() {
        return merchant;
    }

    @Column(name = "invoice_status")
    @Enumerated(EnumType.STRING)
    public InvoiceStatusEnum getInvoiceStatus() {
        return invoiceStatus;
    }

    @Column(name = "payment_due_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    @Column(name = "quantity")
    public Long getQuantity() {
        return quantity;
    }
}
