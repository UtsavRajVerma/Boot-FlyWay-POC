package com.example.flyway_poc.configuration;


import com.example.flyway_poc.entity.d.TargetUOD;
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
        "com.example.flyway_poc.repository.d",
        entityManagerFactoryRef = "dEntityManagerFactory",
        transactionManagerRef = "dTransactionManager"
)
public class DDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.d")
    public DataSourceProperties dDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.d.configuration")
    public DataSource dDataSource() {
        return dDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
    @Bean(name = "dEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dDataSource())
                .packages(TargetUOD.class)
                .build();
    }
    @Bean
    public PlatformTransactionManager dTransactionManager(
            final @Qualifier("dEntityManagerFactory") LocalContainerEntityManagerFactoryBean dEntityManagerFactory) {
        return new JpaTransactionManager(dEntityManagerFactory.getObject());
    }

}