package com.bwongo.core.user_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.base.utils.EnumValidation.isApprovalStatus;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 9:56 PM
 **/
public record UserApprovalRequestDto(
        Long approvalId,
        String status
) {
    public void validate(){
        Validate.notNull(approvalId, ExceptionType.BAD_REQUEST, APPROVAL_ID_REQUIRED);
        Validate.notEmpty(status, APPROVAL_STATUS_REQUIRED);
        Validate.isTrue(isApprovalStatus(status), ExceptionType.BAD_REQUEST, INVALID_APPROVAL_STATUS);
    }
}
