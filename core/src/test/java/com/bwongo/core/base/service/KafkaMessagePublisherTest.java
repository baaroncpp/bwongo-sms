package com.bwongo.core.base.service;

import com.bwongo.commons.models.dto.NotificationDto;
import com.bwongo.commons.models.dto.NotificationStatusEnum;
import com.bwongo.commons.models.dto.NotificationTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/29/24
 * @LocalTime 3:14 PM
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class KafkaMessagePublisherTest {

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest").asCompatibleSubstituteFor("apache/kafka"));

    @DynamicPropertySource
    static void dynamicConfiguration(DynamicPropertyRegistry registry) {
        // Kafka properties
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    void sendNotificationToTopic() {
        var notificationDto = NotificationDto.builder()
                .sender("testSender")
                .recipient("testRecipient")
                .message("test message")
                .notificationType(NotificationTypeEnum.SMS)
                .status(NotificationStatusEnum.PENDING)
                .internalReference("testInternalReference")
                .build();

        kafkaMessagePublisher.sendNotificationToTopic(notificationDto);
        await().pollInterval(Duration.ofSeconds(3))
                .atMost(Duration.ofSeconds(10)).untilAsserted(() -> {

                });
    }
}