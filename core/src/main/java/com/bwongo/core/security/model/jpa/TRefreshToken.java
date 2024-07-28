package com.bwongo.core.security.model.jpa;

import com.bwongo.core.base.model.jpa.BaseEntity;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * @Author bkaaron
 * @Date 3/10/24
 * @LocalTime 5:45 PM
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "t_refresh_token", schema = "core")
public class TRefreshToken extends BaseEntity {
    private String token;
    private Instant expiryDate;
    private TUser user;

    @Column(name = "token")
    public String getToken() {
        return token;
    }

    @Column(name = "expiry_date")
    public Instant getExpiryDate() {
        return expiryDate;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getUser() {
        return user;
    }
}
