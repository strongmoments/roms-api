package com.roms.api.service;

import com.roms.api.config.TenantContext;
import org.codehaus.plexus.util.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component("currentTenantIdentifierResolver")
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getTenantId();
        if (!StringUtils.isEmpty(tenantId)) {
            return tenantId;
        } else {
            // Allow bootstrapping the EntityManagerFactory, in which case no tenant is needed
            return "BOOTSTRAP";
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
