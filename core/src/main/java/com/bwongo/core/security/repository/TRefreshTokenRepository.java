package com.bwongo.core.security.repository;

import com.bwongo.core.security.model.jpa.TRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 5:55 PM
 **/
@Repository
public interface TRefreshTokenRepository extends JpaRepository<TRefreshToken, Long> {
    Optional<TRefreshToken> findByToken(String token);
}
