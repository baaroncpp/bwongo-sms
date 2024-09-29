package com.bwongo.core.sms_mgt.repository;

import com.bwongo.core.base.model.enums.PaymentStatusEnum;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.sms_mgt.models.jpa.TSms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 9:15 PM
 **/
@Repository
public interface TSmsRepository extends JpaRepository<TSms, Long> {

    @Query("SELECT e FROM TSms e WHERE FUNCTION('MONTH', e.createdOn) = :month AND FUNCTION('YEAR', e.createdOn) = :year")
    List<TSms> findAllByMonth(@Param("month") int month, @Param("year") int year);

    List<TSms> findAllByMerchantAndPaymentStatus(TMerchant merchant, PaymentStatusEnum paymentStatus);
    Page<TSms> findAllByMerchant(TMerchant merchant, Pageable pageable);
}
