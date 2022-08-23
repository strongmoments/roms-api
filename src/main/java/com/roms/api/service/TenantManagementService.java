package com.roms.api.service;

import org.springframework.stereotype.Service;

@Service
public interface TenantManagementService {

    void createTenant(String tenantId, String schema);
}
