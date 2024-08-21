package com.bwongo.core.account_mgt.repository;

import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.account_mgt.models.jpa.TAccountTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:33 PM
 **/
@Repository
public interface TAccountTransactionRepository extends JpaRepository<TAccountTransaction, Long> {
    Page<TAccountTransaction> findAllByAccount(TAccount account, Pageable pageable);
}
