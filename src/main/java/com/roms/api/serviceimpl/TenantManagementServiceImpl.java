package com.roms.api.serviceimpl;

import com.roms.api.model.Tenant;
import com.roms.api.repository.TenantRepository;
import com.roms.api.service.TenantManagementService;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import javax.sql.DataSource;

public class TenantManagementServiceImpl implements TenantManagementService {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    //@Qualifier("liquibaseProperties")
    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;
    private final TenantRepository tenantRepository;

    @Autowired
    public TenantManagementServiceImpl(DataSource dataSource,
                                       JdbcTemplate jdbcTemplate,
                                      // @Qualifier("liquibaseProperties")
                                               LiquibaseProperties liquibaseProperties,
                                       ResourceLoader resourceLoader,
                                       TenantRepository tenantRepository) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.liquibaseProperties = liquibaseProperties;
        this.resourceLoader = resourceLoader;
        this.tenantRepository = tenantRepository;
    }

    private static final String VALID_SCHEMA_NAME_REGEXP = "[A-Za-z0-9_]*";

    @Override
    public void createTenant(String tenantId, String schema) {

        // Verify schema string to prevent SQL injection
        if (!schema.matches(VALID_SCHEMA_NAME_REGEXP)) {
            //@todo need to create throw new TenantCreationException("Invalid schema name: " + schema);
        }

        try {
            createSchema(schema);
            runLiquibase(dataSource, schema);
        } catch (DataAccessException e) {
          // @todo  throw new TenantCreationException("Error when creating schema: " + schema, e);
        } catch (LiquibaseException e) {
            // @todo throw new TenantCreationException("Error when populating schema: ", e);
        }
        Tenant tenant = Tenant.builder()
                .tenantId(tenantId)
                .schema(schema)
                .build();
        tenantRepository.save(tenant);
    }

    private void createSchema(String schema) {
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA " + schema));
    }

    private void runLiquibase(DataSource dataSource, String schema) throws LiquibaseException {
        SpringLiquibase liquibase = getSpringLiquibase(dataSource, schema);
        liquibase.afterPropertiesSet();
    }

    protected SpringLiquibase getSpringLiquibase(DataSource dataSource, String schema) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        return liquibase;
    }
}
