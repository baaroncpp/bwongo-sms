package com.bwongo.notification_service.base.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import static com.bwongo.notification_service.base.utils.BaseMsgConstants.SMS_UPDATE_TOPIC;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/12/24
 * @Time 4:17 AM
 **/
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(SMS_UPDATE_TOPIC)
                .build();
    }
}
