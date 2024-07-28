package com.bwongo.core.base.repository;

import com.bwongo.core.base.model.jpa.TCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/12/24
 * @LocalTime 3:33 PM
 **/
@Repository
public interface TCountryRepository extends JpaRepository<TCountry, Long> {
    boolean existsByCountryCode(int countryCode);
    boolean existsByName(String name);
}
