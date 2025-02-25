/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Core backend application configuration.
 */
@ComponentScan(basePackages = {"com.github.aleksikangas.backend.*"})
@Configuration
@EnableJpaRepositories(basePackages = {"com.github.aleksikangas.backend.*"})
@EntityScan(basePackages = {"com.github.aleksikangas.backend.*"})
public class BackendConfiguration {

  @Bean
  public DataSourceProperties dataSourceProperties() {
    final DataSourceProperties dataSourceProperties = new DataSourceProperties();
    dataSourceProperties.setDriverClassName("org.postgresql.Driver");
    dataSourceProperties.setPassword(System.getenv("DATABASE_PASSWORD"));
    dataSourceProperties.setUrl(System.getenv("DATABASE_URL"));
    dataSourceProperties.setUsername(System.getenv("DATABASE_USERNAME"));
    return dataSourceProperties;
  }

  @Bean
  public DataSource dataSource(@Autowired final DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired final DataSource dataSource) {
    final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setGenerateDdl(true);

    final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setPackagesToScan("com.github.aleksikangas.backend");
    factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
    return factoryBean;
  }

  @Bean
  public PlatformTransactionManager transactionManager(@Autowired final EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
