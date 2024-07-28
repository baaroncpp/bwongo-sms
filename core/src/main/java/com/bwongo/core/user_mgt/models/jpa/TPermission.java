package com.bwongo.core.user_mgt.models.jpa;

import com.bwongo.core.base.model.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:19 PM
 **/
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_permission", schema = "core")
@Setter
public class TPermission extends BaseEntity {
    private TRole role;
    private String name;
    private Boolean isAssignable;

    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TRole getRole() {
        return role;
    }

    @Column(name = "permission_name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "is_assignable", nullable = false)
    public Boolean getIsAssignable() {
        return isAssignable;
    }
}
