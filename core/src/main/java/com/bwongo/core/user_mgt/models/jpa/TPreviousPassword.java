package com.bwongo.core.user_mgt.models.jpa;

import com.bwongo.core.base.model.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:22 PM
 **/
@Entity
@Setter
@Table(name = "t_previous_password", schema = "core")
public class TPreviousPassword extends BaseEntity {
    private TUser user;
    private String previousPassword;
    private int passwordChangeCount;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getUser() {
        return user;
    }

    @Column(name = "previous_password")
    public String getPreviousPassword() {
        return previousPassword;
    }

    @Column(name = "password_change_count")
    public int getPasswordChangeCount() {
        return passwordChangeCount;
    }
}
