package com.bwongo.core.security.model.dto;

import com.bwongo.core.user_mgt.models.dto.response.UserGroupResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 5:50 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private UserGroupResponseDto userGroup;
    private List<String> authorities;
}
