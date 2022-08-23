package com.roms.api.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "multitenancy.tenant.liquibase.enabled", havingValue = "true", matchIfMissing = true)
public class TenantLiquibaseConfig {

    /*@Bean
    @ConfigurationProperties("multitenancy.tenant.liquibase")
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }*/

    @Bean
    public DynamicSchemaBasedMultiTenantSpringLiquibase tenantLiquibase() {
        return new DynamicSchemaBasedMultiTenantSpringLiquibase();
    }

}
