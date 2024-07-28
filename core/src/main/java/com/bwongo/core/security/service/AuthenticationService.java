package com.bwongo.core.security.service;

import com.bwongo.commons.exceptions.InsufficientAuthenticationException;
import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.security.model.SecurityUserDetails;
import com.bwongo.core.security.model.dto.AuthenticationRequestDto;
import com.bwongo.core.security.model.dto.AuthenticationResponseDto;
import com.bwongo.core.security.model.dto.RefreshTokenRequestDto;
import com.bwongo.core.security.model.jpa.TRefreshToken;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import com.bwongo.core.user_mgt.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 5:43 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TUserRepository userRepository;
    private final SecurityUserService securityUserService;
    private final RefreshTokenService refreshTokenService;
    private final UserDtoService userDtoService;

    private static final String REFRESH_TOKEN_NOT_FOUND = "Refresh Token is not found";

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        log.warn("authenticator reached");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getEmail(),
                        authenticationRequestDto.getPassword()
                )
        );
        System.out.println(authentication.isAuthenticated());
        Validate.isTrue(authentication.isAuthenticated(), ExceptionType.BAD_CREDENTIALS, "Invalid user !");

        var user = userRepository.findByUsername(authenticationRequestDto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        var username = user.getUsername();
        var authorities = securityUserService.getSecurityUserDetails(username).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var jwtToken = jwtService.generateToken(getMappedSecurityUserDetails(username));
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshTokenService.createRefreshToken(username).getToken())
                .id(user.getId())
                .userGroup(userDtoService.mapTUserGroupToUserGroupResponseDto(user.getUserGroup()))
                .authorities(authorities)
                .build();
    }

    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(TRefreshToken::getUser)
                .map(user -> {
                    var accessToken = jwtService.generateToken(getMappedSecurityUserDetails(user.getUsername()));
                    var authorities = securityUserService.getSecurityUserDetails(user.getUsername()).getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList();
                    return AuthenticationResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenService.createRefreshToken(user.getUsername()).getToken())
                            .id(user.getId())
                            .userGroup(userDtoService.mapTUserGroupToUserGroupResponseDto(user.getUserGroup()))
                            .authorities(authorities)
                            .build();
                })
                .orElseThrow(() -> new InsufficientAuthenticationException(REFRESH_TOKEN_NOT_FOUND));
    }

    private SecurityUserDetails getMappedSecurityUserDetails(String username){
        return securityUserService.getSecurityUserDetails(username);
    }
}
