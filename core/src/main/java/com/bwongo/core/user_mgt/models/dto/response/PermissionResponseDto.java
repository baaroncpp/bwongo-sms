package com.bwongo.core.user_mgt.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:27 PM
 **/
public record PermissionResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        RoleResponseDto role,
        String name,
        Boolean isAssignable
) {
}
