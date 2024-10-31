package com.bwongo.core.security.service;

import com.bwongo.commons.exceptions.BadCredentialsException;
import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.repository.TMerchantApiSettingRepository;
import com.bwongo.core.merchant_mgt.repository.TMerchantRepository;
import com.bwongo.core.security.model.SecurityUserDetails;
import com.bwongo.core.user_mgt.models.jpa.TGroupAuthority;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import com.bwongo.core.user_mgt.repository.TGroupAuthorityRepository;
import com.bwongo.core.user_mgt.repository.TUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.*;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 5:56 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityUserService {

    private final TUserRepository userRepository;
    private final TGroupAuthorityRepository groupAuthorityRepository;
    private final TMerchantApiSettingRepository merchantApiSettingRepository;
    private final TMerchantRepository merchantRepository;

    public SecurityUserDetails getSecurityUserDetails(String username) {

        TUser user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("username: %s not found", username))
        );

        isAccountBlocked(user);

        List<TGroupAuthority> groupAuthorities = groupAuthorityRepository.findByUserGroup(user.getUserGroup());
        Validate.notNull(groupAuthorities, ExceptionType.INSUFFICIENT_AUTH, "user %s has no permissions, to any services", user.getEmail());

        Set<SimpleGrantedAuthority> permissions = groupAuthorities
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission().getName()))
                .collect(Collectors.toSet());

        log.info("auth service reached");

        return SecurityUserDetails.builder()
                .id(user.getId())
                .username(user.getEmail())
                .enabled(user.isApproved())
                .accountNonExpired(!user.isAccountExpired())
                .accountNonLocked(!user.isAccountLocked())
                .credentialsNonExpired(!user.isCredentialExpired())
                .authorities(permissions)
                .password(user.getPassword())
                .build();
    }

    public Boolean validateMerchantSecretKey(String merchantCode, String secretKey){
        var merchant = getMerchantByCode(merchantCode);

        var existingMerchantApiSettings = merchantApiSettingRepository.findByMerchant(merchant);
        Validate.isPresent(existingMerchantApiSettings, API_SETTING_NOT_FOUND);
        var apiSettings = existingMerchantApiSettings.get();

        return apiSettings.getApiSecret().equals(secretKey) ? Boolean.TRUE : Boolean.FALSE;
    }

    private void isAccountBlocked(TUser user){
        if(user.isAccountExpired() || user.isAccountLocked() || user.isCredentialExpired()){
            throw new BadCredentialsException("user account is blocked");
        }
    }

    private TMerchant getMerchantByCode(String code){
        var existingMerchant = merchantRepository.findByMerchantCode(code);
        Validate.isPresent(existingMerchant, MERCHANT_WITH_CODE_NOT_FOUND, code);
        return existingMerchant.get();
    }
}
