package com.bwongo.core.merchant_mgt.repository;

import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 3:13 PM
 **/
@Repository
public interface TMerchantRepository extends JpaRepository<TMerchant, Long> {
    boolean existsByEmail(String merchantMail);

    boolean existsByPhoneNumber(String merchantPhoneNumber);
}
