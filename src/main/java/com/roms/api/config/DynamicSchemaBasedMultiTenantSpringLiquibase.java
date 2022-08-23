package com.roms.api.config;

import com.roms.api.model.Tenant;
import com.roms.api.repository.TenantRepository;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.util.Collection;

@Slf4j
public class DynamicSchemaBasedMultiTenantSpringLiquibase implements InitializingBean, ResourceLoaderAware {

    @Autowired
    private TenantRepository masterTenantRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("liquibaseProperties")
    private LiquibaseProperties liquibaseProperties2;

    @Autowired
    private ResourceLoader resourceLoader;

    public DynamicSchemaBasedMultiTenantSpringLiquibase() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Schema based multitenancy enabled");
        this.runOnAllSchemas(dataSource, masterTenantRepository.findAll());
    }

    protected void runOnAllSchemas(DataSource dataSource, Collection<Tenant> tenants) throws LiquibaseException {
        for(Tenant tenant : tenants) {
            log.info("Initializing Liquibase for tenant " + tenant.getTenantId());
            SpringLiquibase liquibase = this.getSpringLiquibase(dataSource, tenant.getSchema());
            liquibase.afterPropertiesSet();
            log.info("Liquibase ran for tenant " + tenant.getTenantId());
        }
    }

    protected SpringLiquibase getSpringLiquibase(DataSource dataSource, String schema) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(new DefaultResourceLoader());
        //liquibase.setResourceLoader(resourceLoader.getResource("db"));
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        liquibase.setChangeLog(liquibaseProperties2.getChangeLog());
        liquibase.setContexts(liquibaseProperties2.getContexts());
        return liquibase;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }
}

