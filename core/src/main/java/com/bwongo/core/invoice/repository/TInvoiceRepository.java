package com.bwongo.core.invoice.repository;

import com.bwongo.core.invoice.models.jpa.TInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/20/24
 * @Time 8:11 PM
 **/
@Repository
public interface TInvoiceRepository extends JpaRepository<TInvoice, Long> {
}
