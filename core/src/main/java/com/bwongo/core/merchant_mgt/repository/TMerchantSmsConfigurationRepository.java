package com.bwongo.core.merchant_mgt.repository;

import com.bwongo.core.merchant_mgt.models.jpa.TMerchantSmsConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 3:14 PM
 **/
@Repository
public interface TMerchantSmsConfigurationRepository extends JpaRepository<TMerchantSmsConfiguration, Long> {
}
