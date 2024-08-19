package com.bwongo.core.account_mgt.repository;

import com.bwongo.core.account_mgt.models.jpa.TAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:33 PM
 **/
public interface TAccountTransactionRepository extends JpaRepository<TAccountTransaction, Long> {
}
