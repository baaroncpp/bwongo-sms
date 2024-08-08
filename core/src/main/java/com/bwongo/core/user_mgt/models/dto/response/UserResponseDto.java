package com.bwongo.core.user_mgt.models.dto.response;

import com.bwongo.core.base.model.enums.UserTypeEnum;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;

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
        String firstName,
        String secondName,
        String email,
        boolean accountLocked,
        boolean accountExpired,
        boolean credentialExpired,
        boolean approved,
        UserGroupResponseDto userGroup,
        Boolean isDeleted,
        Long approvedBy,
        UserTypeEnum userType,
        Boolean nonVerifiedEmail,
        String imagePath,
        Long merchantId
) {
}
