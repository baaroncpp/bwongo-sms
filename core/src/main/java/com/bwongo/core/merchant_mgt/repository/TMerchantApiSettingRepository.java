package com.bwongo.core.merchant_mgt.repository;

import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantApiSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 10:18AM
 **/
@Repository
public interface TMerchantApiSettingRepository extends JpaRepository<TMerchantApiSetting, Long> {
    Optional<TMerchantApiSetting> findByMerchant(TMerchant merchant);
    List<TMerchantApiSetting> findAllByCredentialExpired(boolean expired);
}
