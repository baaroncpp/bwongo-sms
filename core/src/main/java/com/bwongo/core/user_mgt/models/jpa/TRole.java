package com.bwongo.core.user_mgt.models.jpa;

import com.bwongo.core.base.model.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 5:18 PM
 **/
@Entity
@Table(name = "t_role", schema = "core")
public class TRole extends BaseEntity {
    private String name;
    private String note;

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
