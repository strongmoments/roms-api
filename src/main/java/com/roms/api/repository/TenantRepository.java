package com.roms.api.repository;

import com.roms.api.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface TenantRepository  extends JpaRepository<Tenant, String> {
        @Query("select t from Tenant t where t.tenantId = :tenantId")
        Optional<Tenant> findByTenantId(@Param("tenantId") String tenantId);
}