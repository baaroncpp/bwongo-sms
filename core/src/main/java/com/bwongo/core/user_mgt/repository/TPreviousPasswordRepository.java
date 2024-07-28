package com.bwongo.core.user_mgt.repository;

import com.bwongo.core.user_mgt.models.jpa.TPreviousPassword;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 4:02 PM
 **/
@Repository
public interface TPreviousPasswordRepository extends JpaRepository<TPreviousPassword, Long> {
    List<TPreviousPassword> findAllByUser(TUser user);
}
