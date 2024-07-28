package com.bwongo.core.base.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.DateTimeUtil;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.jpa.AuditEntity;
import com.bwongo.core.base.model.jpa.BaseEntity;
import com.bwongo.core.security.model.SecurityUserDetails;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/12/24
 * @LocalTime 4:17 PM
 **/
@Service
public class AuditService {
    private static final String ONLY_LOGGED_IN_USER = "Only a logged in user can make this change";

    public void stampLongEntity(BaseEntity entity) {
        Date date = DateTimeUtil.getCurrentUTCTime();
        if(entity.getId() == null){
            entity.setCreatedOn(date);
            entity.setModifiedOn(date);
        }
        entity.setModifiedOn(date);
    }

    public void stampAuditedEntity(AuditEntity auditEntity) {
        SecurityUserDetails user = getLoggedInUser();
        Validate.notNull(user, ExceptionType.BAD_CREDENTIALS, ONLY_LOGGED_IN_USER);
        Date date = DateTimeUtil.getCurrentUTCTime();
        var tUser = new TUser();
        tUser.setId(user.getId());

        if(auditEntity.getId() == null){
            auditEntity.setCreatedOn(date);
            auditEntity.setCreatedBy(tUser);
        }

        auditEntity.setModifiedBy(tUser);
        auditEntity.setModifiedOn(date);
    }

    public SecurityUserDetails getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            return (SecurityUserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
