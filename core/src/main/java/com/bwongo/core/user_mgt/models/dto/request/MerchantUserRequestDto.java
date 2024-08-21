package com.bwongo.core.user_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.text.StringRegExUtil;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.base.utils.EnumValidation.isUserType;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/20/24
 * @LocalTime 9:43 AM
 **/
public record MerchantUserRequestDto(
        String firstName,
        String secondName,
        String email,
        String password
) {
    public void validate(){
        Validate.notEmpty(firstName, FIRST_NAME_REQUIRED);
        Validate.notEmpty(secondName, SECOND_NAME_REQUIRED);
        Validate.notEmpty(email, EMAIL_REQUIRED);
        Validate.notEmpty(password, PASSWORD_REQUIRED);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        StringRegExUtil.stringOfStandardPassword(password, STANDARD_PASSWORD);
    }
}
