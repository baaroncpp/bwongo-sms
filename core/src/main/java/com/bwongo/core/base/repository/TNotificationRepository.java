package com.bwongo.core.base.repository;

import com.bwongo.core.base.model.jpa.TNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/16/24
 * @Time 11:40 AM
 **/
@Repository
public interface TNotificationRepository extends JpaRepository<TNotification, Long> {
}
