package com.bwongo.core.user_mgt.models.dto.request;

import com.bwongo.commons.text.StringRegExUtil;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:12 PM
 **/
public record UserGroupRequestDto(
        String name,
        String note
) {
    public void validate(){
        Validate.notEmpty(name, USER_GROUP_NAME_IS_NULL);
        Validate.notEmpty(note, USER_GROUP_NOTE_IS_NULL);
        StringRegExUtil.stringOfOnlyUpperCase(name, USER_GROUP_NAME_ONLY_UPPER_CASE);
    }
}
