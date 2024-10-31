package com.bwongo.sms_integration_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SmsIntegrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsIntegrationServiceApplication.class, args);
	}

}
