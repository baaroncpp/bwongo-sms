package com.bwongo.core.user_mgt.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:26 PM
 **/
public record GroupAuthorityResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserGroupResponseDto userGroup,
        PermissionResponseDto permission
) {
}
