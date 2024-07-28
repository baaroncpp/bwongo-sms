package com.bwongo.core.user_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.ApprovalStatusEnum;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 9:57 PM
 **/
public record UserApprovalResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        UserResponseDto user,
        ApprovalStatusEnum status
) {
}
