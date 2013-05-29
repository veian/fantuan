package com.ozhou.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.ozhou.utils.LoggingAuditSetup;

public class ContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger logger = LoggerFactory
			.getLogger(ContextInitializer.class);

	public void initialize(ConfigurableApplicationContext applicationContext) {
		logger.debug("Context is initialized");
		LoggingAuditSetup.setup();
	}
}
