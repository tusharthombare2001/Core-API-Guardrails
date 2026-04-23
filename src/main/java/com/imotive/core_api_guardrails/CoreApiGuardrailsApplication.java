package com.imotive.core_api_guardrails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class CoreApiGuardrailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApiGuardrailsApplication.class, args);
	}

}
