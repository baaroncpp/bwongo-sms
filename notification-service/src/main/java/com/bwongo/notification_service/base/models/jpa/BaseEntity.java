package com.bwongo.notification_service.base.models.jpa;

import jakarta.persistence.*;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 12:41 PM
 **/
@MappedSuperclass
@Setter
public class BaseEntity implements Serializable {
    private Long id;
    private Date createdOn;
    private Date modifiedOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedOn() {
        return createdOn;
    }

    @Column(name = "modified_on")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifiedOn() {
        return modifiedOn;
    }
}
