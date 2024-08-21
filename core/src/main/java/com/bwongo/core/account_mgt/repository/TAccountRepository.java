package com.bwongo.core.account_mgt.repository;

import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:32 PM
 **/
@Repository
public interface TAccountRepository extends JpaRepository<TAccount, Long> {
    Optional<TAccount> findByMerchant(TMerchant merchant);
}
