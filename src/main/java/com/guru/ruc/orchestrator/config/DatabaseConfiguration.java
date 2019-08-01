package com.guru.ruc.orchestrator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.guru.ruc.orchestrator.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration  {
}
