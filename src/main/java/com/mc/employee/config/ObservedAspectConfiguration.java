package com.mc.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mc.employee.log.SimpleObervabilityLoggingHandler;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;

/**
 * This is Observability tracing logs. 
 */
@Configuration
public class ObservedAspectConfiguration {
	@Bean
	ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
		observationRegistry.observationConfig().observationHandler(new SimpleObervabilityLoggingHandler());
		return new ObservedAspect(observationRegistry);
	}
}
