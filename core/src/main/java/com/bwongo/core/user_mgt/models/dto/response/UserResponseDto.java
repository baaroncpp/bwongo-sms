package com.bwongo.core.user_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.UserTypeEnum;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 6:00 PM
 **/
public record UserResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String username,
        boolean accountLocked,
        boolean accountExpired,
        boolean credentialExpired,
        boolean approved,
        UserGroupResponseDto userGroup,
        Boolean isDeleted,
        Long approvedBy,
        UserTypeEnum userType
) {
}
