package com.bwongo.core.account_mgt.models.jpa;

import com.bwongo.core.base.model.jpa.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/24/24
 * @Time 12:29 PM
 **/
@Entity
@Setter
@Table(name = "t_account_top_up", schema = "core")
public class TAccountTopUp extends AuditEntity {
}
