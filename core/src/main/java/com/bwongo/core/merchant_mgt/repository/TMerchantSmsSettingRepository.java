package com.bwongo.core.merchant_mgt.repository;

import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantSmsSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 3:14 PM
 **/
@Repository
public interface TMerchantSmsSettingRepository extends JpaRepository<TMerchantSmsSetting, Long> {
    Optional<TMerchantSmsSetting> findByMerchant(TMerchant merchant);
}
