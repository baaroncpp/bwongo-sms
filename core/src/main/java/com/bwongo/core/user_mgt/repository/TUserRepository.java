package com.bwongo.core.user_mgt.repository;

import com.bwongo.core.base.model.enums.UserTypeEnum;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:25 PM
 **/
@Repository
public interface TUserRepository extends JpaRepository<TUser, Long> {
    Optional<TUser> findByUsername(String username);

    Long countByUserType(UserTypeEnum userTypeEnum);

    boolean existsByUsername(String username);
}
