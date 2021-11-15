package com.example.flyway_poc.configuration;

import com.example.flyway_poc.entity.a.SourceUser;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages =
        "com.example.flyway_poc.repository.a",
        entityManagerFactoryRef = "aEntityManagerFactory",
        transactionManagerRef = "aTransactionManager"
)
public class ADataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.a")
    public DataSourceProperties aDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.a.configuration")
    public DataSource aDataSource() {
        return aDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "aEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean aEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(aDataSource())
                .packages(SourceUser.class)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager aTransactionManager(
            final @Qualifier("aEntityManagerFactory") LocalContainerEntityManagerFactoryBean aEntityManagerFactory) {
        return new JpaTransactionManager(aEntityManagerFactory.getObject());
    }
}