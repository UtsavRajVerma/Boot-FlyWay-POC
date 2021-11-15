package com.example.flyway_poc.configuration;

import com.example.flyway_poc.entity.c.SourceUOD;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages =
        "com.example.flyway_poc.repository.c",
        entityManagerFactoryRef = "cEntityManagerFactory",
        transactionManagerRef = "cTransactionManager"
)
public class CDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.c")
    public DataSourceProperties cDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.c.configuration")
    public DataSource cDataSource() {
        return cDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
    @Bean(name = "cEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean cEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(cDataSource())
                .packages(SourceUOD.class)
                .build();
    }
    @Bean
    public PlatformTransactionManager cTransactionManager(
            final @Qualifier("cEntityManagerFactory") LocalContainerEntityManagerFactoryBean cEntityManagerFactory) {
        return new JpaTransactionManager(cEntityManagerFactory.getObject());
    }

}