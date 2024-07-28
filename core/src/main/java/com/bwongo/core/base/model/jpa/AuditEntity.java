package com.bwongo.core.base.model.jpa;

import com.bwongo.core.user_mgt.models.jpa.TUser;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 **/
@MappedSuperclass
@Setter
@ToString
public class AuditEntity extends BaseEntity {
    private TUser modifiedBy;
    private TUser createdBy;
    private boolean isDeleted;

    @JoinColumn(name = "modified_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getModifiedBy() {
        return modifiedBy;
    }

    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getCreatedBy() {
        return createdBy;
    }

    @Column(name = "is_deleted")
    public boolean isDeleted() {
        return isDeleted;
    }
}
