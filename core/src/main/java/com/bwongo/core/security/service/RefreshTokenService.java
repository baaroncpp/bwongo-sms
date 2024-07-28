package com.bwongo.core.security.service;

import com.bwongo.commons.exceptions.InsufficientAuthenticationException;
import com.bwongo.commons.utils.DateTimeUtil;
import com.bwongo.core.security.model.jpa.TRefreshToken;
import com.bwongo.core.security.repository.TRefreshTokenRepository;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

import static com.bwongo.commons.text.StringUtil.randomString;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 5:54 PM
 **/
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final TUserRepository userRepository;
    private final TRefreshTokenRepository refreshTokenRepository;

    private static final String EXPIRED_REFRESH_TOKEN = " Refresh token is expired. Please make a new login..!";

    public TRefreshToken createRefreshToken(String username){
        var date = DateTimeUtil.getCurrentUTCTime();
        var refreshToken = TRefreshToken.builder()
                .user(userRepository.findByUsername(username).get())
                .token(randomString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();

        refreshToken.setCreatedOn(date);
        refreshToken.setModifiedOn(date);

        return refreshTokenRepository.save(refreshToken);
    }

    public TRefreshToken verifyExpiration(TRefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new InsufficientAuthenticationException(token.getToken() + EXPIRED_REFRESH_TOKEN);
        }
        return token;
    }

    public Optional<TRefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
