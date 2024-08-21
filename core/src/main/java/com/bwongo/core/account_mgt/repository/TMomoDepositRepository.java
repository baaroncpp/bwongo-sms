package com.bwongo.core.account_mgt.repository;

import com.bwongo.core.account_mgt.models.jpa.TMomoDeposit;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:44 PM
 **/
@Repository
public interface TMomoDepositRepository extends JpaRepository<TMomoDeposit, Long> {
    Page<TMomoDeposit> findAllByMerchant(TMerchant merchant, Pageable pageable);
}
