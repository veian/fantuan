package com.ozhou.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class LoggingAuditSetup {

	static final Logger logger = LoggerFactory
			.getLogger(LoggingAuditSetup.class);

	public static void setup() {
		java.util.logging.LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();
	}
}
