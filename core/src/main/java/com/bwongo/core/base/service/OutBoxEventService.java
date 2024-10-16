package com.bwongo.core.base.service;

import com.bwongo.commons.models.dto.NotificationDto;
import com.bwongo.commons.models.event.NotificationEvent;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.enums.EventStatus;
import com.bwongo.core.base.model.jpa.TNotification;
import com.bwongo.core.base.repository.TNotificationRepository;
import com.bwongo.core.base.repository.TOutBoxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bwongo.core.base.utils.BaseMsgUtils.NOTIFICATION_NOT_FOUND;
import static com.bwongo.core.base.utils.BaseUtils.convertNotificationToJson;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/16/24
 * @Time 12:40PM
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class OutBoxEventService {

    private final TOutBoxEventRepository outBoxEventRepository;
    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final TNotificationRepository notificationRepository;
    private final AuditService auditService;

    public void outBoxEventPublisher(){

        var events = outBoxEventRepository.findAllByStatus(EventStatus.PENDING);

        events.forEach(event -> {
            try {
                var notificationId = event.getAggregatorId();
                var notification = getNotification(notificationId);

                var notificationEvent = new NotificationEvent(notificationToDto(notification));
                var eventId = notificationEvent.getEventId();

                kafkaMessagePublisher.sendNotificationToTopic(notificationEvent);

                notification.setEventId(eventId.toString());
                auditService.stampLongEntity(notification);
                var updatedNotification = notificationRepository.save(notification);

                event.setStatus(EventStatus.SENT);
                event.setPayload(convertNotificationToJson(updatedNotification));
                auditService.stampLongEntity(event);
                outBoxEventRepository.save(event);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        });
    }

    private NotificationDto notificationToDto(TNotification tNotification){
        return NotificationDto.builder()
                .sender(tNotification.getSender())
                .recipient(tNotification.getRecipient())
                .message(tNotification.getMessage())
                .status(tNotification.getStatus())
                .merchantCode(tNotification.getMerchantCode())
                .notificationType(tNotification.getNotificationType())
                .internalReference(tNotification.getInternalReference())
                .build();
    }

    private TNotification getNotification(Long id){
        var existingNotification = notificationRepository.findById(id);
        Validate.isPresent(existingNotification, NOTIFICATION_NOT_FOUND);
        return existingNotification.get();
    }
}
