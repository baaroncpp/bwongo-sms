package com.bwongo.core.account_mgt.repository;

import com.bwongo.core.account_mgt.models.jpa.TCashflow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:34 PM
 **/
public interface TCashflowRepository extends JpaRepository<TCashflow, Long> {
}
