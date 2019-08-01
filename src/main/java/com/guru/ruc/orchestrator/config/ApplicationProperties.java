package com.guru.ruc.orchestrator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to OrchestratorApplication.
 * <p>
 * Properties are configured in the {@code application.properties} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
