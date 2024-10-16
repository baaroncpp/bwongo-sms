package com.bwongo.core.base.repository;

import com.bwongo.core.base.model.enums.EventStatus;
import com.bwongo.core.base.model.jpa.TOutBoxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/16/24
 * @Time 11:40 AM
 **/
@Repository
public interface TOutBoxEventRepository extends JpaRepository<TOutBoxEvent, Long> {
    List<TOutBoxEvent> findAllByStatus(EventStatus eventStatus);
}
