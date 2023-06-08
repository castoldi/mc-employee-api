package com.mc.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	@Bean
	OpenAPI usersMicroserviceOpenAPI() {
		return new OpenAPI().info(new Info().title("MC Employee API").description("Employee data maintanance API").version("0.0.1"));
	}
}
