package com.bwongo.core.user_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.text.StringRegExUtil;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.base.utils.EnumValidation.isUserType;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 8:35 PM
 **/
public record UserRequestDto(
        String username,
        String password,
        Long userGroupId,
        String userType
) {
    public void validate(){
        Validate.notEmpty(username, USERNAME_REQUIRED);
        Validate.notEmpty(password, PASSWORD_REQUIRED);
        Validate.notNull(userGroupId, ExceptionType.BAD_REQUEST, USER_GROUP_ID_REQUIRED);
        Validate.notNull(userType, ExceptionType.BAD_REQUEST, USER_TYPE_REQUIRED);
        Validate.isTrue(isUserType(userType), ExceptionType.BAD_REQUEST, VALID_USER_TYPE);
        StringRegExUtil.stringOfOnlyNumbersAndChars(username, USERNAME_SHOULD_CONTAIN_ONLY_CHARS_AND_NUMBERS);
        StringRegExUtil.stringOfStandardPassword(password, STANDARD_PASSWORD);
    }
}
