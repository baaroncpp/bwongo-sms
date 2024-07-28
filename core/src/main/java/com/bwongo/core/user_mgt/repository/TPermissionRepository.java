package com.bwongo.core.user_mgt.repository;

import com.bwongo.core.user_mgt.models.jpa.TPermission;
import com.bwongo.core.user_mgt.models.jpa.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:08 PM
 **/
@Repository
public interface TPermissionRepository extends JpaRepository<TPermission, Long> {
    List<TPermission> findByIsAssignableEquals(Boolean isAssignable);
    Optional<TPermission> findByName(String name);
    List<TPermission> findAllByRole(TRole role);
}
