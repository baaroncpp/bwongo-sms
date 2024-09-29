package com.bwongo.core.invoice.models.jpa;

import com.bwongo.core.base.model.jpa.BaseEntity;
import com.bwongo.core.sms_mgt.models.jpa.TSms;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/20/24
 * @Time 8:04 PM
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "t_invoice_item", schema = "core",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"invoice", "sms"})
        })
public class TInvoiceItem extends BaseEntity {
    private TInvoice invoice;
    private TSms sms;

    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public TInvoice getInvoice() {
        return invoice;
    }

    @JoinColumn(name = "sms_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public TSms getSms() {
        return sms;
    }
}
