package com.bwongo.core.user_mgt.models.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:52 PM
 **/
public record UserGroupResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String name,
        String note
) {
}
