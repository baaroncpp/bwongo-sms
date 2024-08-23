package com.bwongo.core.sms_mgt.repository;

import com.bwongo.core.sms_mgt.models.jpa.TSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 9:15 PM
 **/
@Repository
public interface TSmsRepository extends JpaRepository<TSms, Long> {
}
