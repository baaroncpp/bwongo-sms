package com.bwongo.core.user_mgt.models.jpa;

import com.bwongo.core.base.model.enums.ApprovalStatusEnum;
import com.bwongo.core.base.model.jpa.AuditEntity;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:21 PM
 **/
@Entity
@Table(name = "t_user_approval", schema = "core")
@Setter
public class TUserApproval extends AuditEntity {
    private TUser user;
    private ApprovalStatusEnum status;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getUser() {
        return user;
    }

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public ApprovalStatusEnum getStatus() {
        return status;
    }
}
