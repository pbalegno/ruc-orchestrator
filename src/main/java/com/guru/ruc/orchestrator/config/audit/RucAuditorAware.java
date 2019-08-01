package com.guru.ruc.orchestrator.config.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.guru.ruc.orchestrator.config.Constants;

@Component("auditorAware")
public class RucAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(Constants.SYSTEM_ACCOUNT);
    }
}
