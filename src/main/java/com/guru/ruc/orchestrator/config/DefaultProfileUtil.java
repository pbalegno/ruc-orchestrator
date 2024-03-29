package com.guru.ruc.orchestrator.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

/**
 * Utility class to load a Spring profile to be used as default
 * when there is no {@code spring.profiles.active} set in the environment or as command line argument.
 * If the value is not available in {@code application.properties} then {@code dev} profile will be used as default.
 */
public final class DefaultProfileUtil {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private DefaultProfileUtil() {
    }

    /**
     * Set a default to use when no profile is configured.
     *
     * @param app the Spring application.
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(SPRING_PROFILE_DEFAULT, Constants.PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
    }
}
