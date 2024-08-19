package com.bwongo.core.base.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.bwongo.core.base.utils.BaseMsgUtils.SMS_TOPIC;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/18/24
 * @LocalTime 9:19AM
 **/
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(SMS_TOPIC)
                .build();
    }
}
