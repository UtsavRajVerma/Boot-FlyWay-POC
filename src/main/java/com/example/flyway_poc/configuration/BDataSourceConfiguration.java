package com.example.flyway_poc.configuration;

import com.example.flyway_poc.entity.b.TargetUser;
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
        "com.example.flyway_poc.repository.b",
        entityManagerFactoryRef = "bEntityManagerFactory",
        transactionManagerRef = "bTransactionManager"
)
public class BDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.b")
    public DataSourceProperties bDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.b.configuration")
    public DataSource bDataSource() {
        return bDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "bEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean bEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(bDataSource())
                .packages(TargetUser.class)
                .build();
    }


    @Bean
    public PlatformTransactionManager bTransactionManager(
            final @Qualifier("bEntityManagerFactory") LocalContainerEntityManagerFactoryBean bEntityManagerFactory) {
        return new JpaTransactionManager(bEntityManagerFactory.getObject());
    }
}