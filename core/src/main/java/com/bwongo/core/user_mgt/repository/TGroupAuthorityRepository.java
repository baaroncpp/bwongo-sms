package com.bwongo.core.user_mgt.repository;

import com.bwongo.core.user_mgt.models.jpa.TGroupAuthority;
import com.bwongo.core.user_mgt.models.jpa.TPermission;
import com.bwongo.core.user_mgt.models.jpa.TUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:26 PM
 **/
@Repository
public interface TGroupAuthorityRepository  extends JpaRepository<TGroupAuthority, Long> {
    List<TGroupAuthority> findByUserGroup(TUserGroup userGroup);
    Optional<TGroupAuthority> findByUserGroupAndPermission(TUserGroup userGroup, TPermission permission);
}
