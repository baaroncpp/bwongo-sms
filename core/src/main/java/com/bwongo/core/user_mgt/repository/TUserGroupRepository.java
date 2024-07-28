package com.bwongo.core.user_mgt.repository;

import com.bwongo.core.user_mgt.models.jpa.TUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:05 PM
 **/
@Repository
public interface TUserGroupRepository extends JpaRepository<TUserGroup, Long> {
    Optional<TUserGroup> findTUserGroupByName(String name);
}
