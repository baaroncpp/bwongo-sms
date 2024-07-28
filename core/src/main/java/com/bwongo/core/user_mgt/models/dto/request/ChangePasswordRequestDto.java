package com.bwongo.core.user_mgt.models.dto.request;

import com.bwongo.commons.text.StringRegExUtil;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:19 PM
 **/
public record ChangePasswordRequestDto(
        String newPassword,
        String oldPassword
) {
    public void validate(){
        Validate.notEmpty(newPassword, NULL_NEW_PASSWORD);
        Validate.notEmpty(oldPassword, NULL_OLD_PASSWORD);
        StringRegExUtil.stringOfStandardPassword(newPassword, STANDARD_PASSWORD);
    }
}
