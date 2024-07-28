package com.bwongo.core.user_mgt.utils;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.user_mgt.models.jpa.TPermission;
import com.bwongo.core.user_mgt.models.jpa.TUser;

import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 2:41 PM
 **/
public class UserMgtUtils {

    private UserMgtUtils() { }

    public static void checkThatUserIsAssignable(TUser user){
        Validate.isTrue(user.isApproved(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_NOT_APPROVED, user.getId());
        Validate.isTrue(!user.getDeleted(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_DELETED);
        Validate.isTrue(!user.isAccountExpired(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_EXPIRED);
        Validate.isTrue(!user.isCredentialExpired(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_CREDENTIALS_EXPIRED);
        Validate.isTrue(!user.isAccountLocked(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_LOCKED);
    }

    public static void checkThatPermissionRoleIsAssignable(TPermission permission){
        Validate.isTrue(permission.getIsAssignable(), ExceptionType.BAD_REQUEST, PERMISSION_IS_IN_ACTIVE);
    }
}
