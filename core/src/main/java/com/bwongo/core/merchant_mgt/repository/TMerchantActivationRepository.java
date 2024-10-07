package com.bwongo.core.merchant_mgt.repository;

import com.bwongo.core.base.model.enums.ActivationCodeStatusEnum;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchantActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/8/24
 * @LocalTime 3:57 PM
 **/
@Repository
public interface TMerchantActivationRepository extends JpaRepository<TMerchantActivation, Long> {
    Optional<TMerchantActivation> findByActivationCodeAndMerchant(String activationCode, TMerchant merchant);
    Optional<TMerchantActivation> findByActivationCode(String activationCode);
    List<TMerchantActivation> findAllByMerchantAndActivationCodeStatus(TMerchant merchant, ActivationCodeStatusEnum activationCodeStatus);
    Optional<TMerchantActivation> findByMerchantAndActive(TMerchant merchant, boolean active);
    List<TMerchantActivation> findAllByCreatedOnBeforeAndActive(Date createdOn, boolean active);
}
