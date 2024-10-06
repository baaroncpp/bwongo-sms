package com.bwongo.core.user_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.text.StringRegExUtil;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.base.utils.EnumValidation.isAdminUserType;
import static com.bwongo.core.base.utils.EnumValidation.isUserType;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 8:35 PM
 **/
public record UserRequestDto(
        String firstName,
        String secondName,
        String email,
        String password,
        String userType
) {
    public void validate(){
        Validate.notEmpty(firstName, FIRST_NAME_REQUIRED);
        Validate.notEmpty(secondName, SECOND_NAME_REQUIRED);
        Validate.notEmpty(email, EMAIL_REQUIRED);
        Validate.notEmpty(password, PASSWORD_REQUIRED);
        Validate.notNull(userType, ExceptionType.BAD_REQUEST, USER_TYPE_REQUIRED);
        Validate.isTrue(isAdminUserType(userType), ExceptionType.BAD_REQUEST, VALID_USER_TYPE);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        StringRegExUtil.stringOfStandardPassword(password, STANDARD_PASSWORD);
    }
}
