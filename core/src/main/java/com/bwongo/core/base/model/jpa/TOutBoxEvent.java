package com.bwongo.core.base.model.jpa;

import com.bwongo.core.base.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/16/24
 * @Time 10:50 AM
 **/
@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_out_box_event", schema = "core")
public class TOutBoxEvent extends BaseEntity{
    private String aggregatorType;
    private Long aggregatorId;
    private String type;
    private String payload;
    private EventStatus status;

    @Column(name = "aggregator_type")
    public String getAggregatorType() {
        return aggregatorType;
    }

    @Column(name = "aggregator_id")
    public Long getAggregatorId() {
        return aggregatorId;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    @Column(name = "payload")
    public String getPayload() {
        return payload;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public EventStatus getStatus() {
        return status;
    }
}
