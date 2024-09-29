package com.bwongo.core.account_mgt.repository;

import com.bwongo.core.account_mgt.models.jpa.TCashFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:34 PM
 **/
@Repository
public interface TCashFlowRepository extends JpaRepository<TCashFlow, Long> {
    Optional<TCashFlow> findByInternalReference(String internalReference);
}
