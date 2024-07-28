package com.bwongo.core.base.repository;

import com.bwongo.core.base.model.jpa.TAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/21/24
 * @LocalTime 3:22 PM
 **/
@Repository
public interface TAddressRepository extends JpaRepository<TAddress, Long> {
}
