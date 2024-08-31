package com.bwongo.core.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/26/24
 * @LocalTime 5:40 PM
 **/
@Testcontainers
@TestConfiguration
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TestContainerConfig {

    // Define the PostgreSQL container
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("testdb")
            .withUsername("testUsername")
            .withPassword("testPassword");

    /*@Bean
    public DataSource dataSource() {

        // Initialize PostgreSQL container and get its JDBC URL
        //postgresContainer.start();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresContainer.getDriverClassName());
        dataSource.setUrl(postgresContainer.getJdbcUrl());
        dataSource.setUsername(postgresContainer.getUsername());
        dataSource.setPassword(postgresContainer.getPassword());
        dataSource.setSchema("core");
        return dataSource;
    }*/

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        // The container is guaranteed to be started here
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.flyway.enabled", () -> true);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration");
    }

    @Test
    void testDatabaseConnection() {
        assertThat(postgresContainer.isRunning()).isTrue();
    }
}
