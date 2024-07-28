package com.bwongo.core.user_mgt.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:21 PM
 **/
public record RoleResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String name,
        String note
) {
}
