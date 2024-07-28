package com.bwongo.core.user_mgt.repository;

import com.bwongo.core.base.model.enums.ApprovalStatusEnum;
import com.bwongo.core.user_mgt.models.jpa.TUserApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 4:01 PM
 **/
@Repository
public interface TUserApprovalRepository extends JpaRepository<TUserApproval, Long> {
    Optional<TUserApproval> findByUserId(Long userId);
    Page<TUserApproval> findAllByStatus(ApprovalStatusEnum status, Pageable pageable);
}
