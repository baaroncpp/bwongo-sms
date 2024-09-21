package com.bwongo.core.invoice.repository;

import com.bwongo.core.invoice.models.jpa.TInvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/20/24
 * @Time 8:10 PM
 **/
@Repository
public interface TInvoiceItemRepository extends JpaRepository<TInvoiceItem, Long> {
}
