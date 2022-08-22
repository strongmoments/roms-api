package com.roms.api.config;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.roms.api.model.Tenant;
import com.roms.api.repository.TenantRepository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.common.cache.CacheBuilder;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private final transient DataSource datasource;
    private final transient TenantRepository tenantRepository;
    private final Long maximumSize;
    private final Integer expireAfterAccess;

    private transient LoadingCache<String, String> tenantSchemas;

    @PostConstruct
    private void createCache() {
        tenantSchemas = (LoadingCache<String, String>) CacheBuilder.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) {
                        Tenant tenant = tenantRepository.findByTenantId(key)
                                .orElseThrow(() -> new RuntimeException("No such tenant: " + key));
                        return tenant.getSchema();
                    }
                });
    }

    @Autowired
    public SchemaBasedMultiTenantConnectionProvider(
            DataSource datasource,
            TenantRepository tenantRepository,
            @Value("${multitenancy.schema-cache.maximumSize:1000}")
                    Long maximumSize,
            @Value("${multitenancy.schema-cache.expireAfterAccess:10}")
                    Integer expireAfterAccess) {
        this.datasource = datasource;
        this.tenantRepository = tenantRepository;
        this.maximumSize = maximumSize;
        this.expireAfterAccess = expireAfterAccess;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws  SQLException {
        log.info("Get connection for tenant {}", tenantIdentifier);
        String tenantSchema = null;
        try {
            tenantSchema = (String) tenantSchemas.get(tenantIdentifier);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantSchema);
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        log.info("Release connection for tenant {}", tenantIdentifier);
        connection.setSchema(null);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType) ) {
            return (T) this;
        } else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }
}
