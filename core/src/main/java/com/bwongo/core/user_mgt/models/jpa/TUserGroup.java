package com.bwongo.core.user_mgt.models.jpa;

import com.bwongo.core.base.model.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:17 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user_group", schema = "core")
public class TUserGroup extends BaseEntity {
    private String name;
    private String note;

    @Column(name = "user_group_name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "group_note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
