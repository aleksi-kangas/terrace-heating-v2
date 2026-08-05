/*
 * Copyright (c) 2025 Aleksi Kangas.
 */

package com.github.aleksikangas.backend.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Core backend application configuration.
 */
@ComponentScan(basePackages = {"com.github.aleksikangas.backend"})
@Configuration
@EnableJpaRepositories(basePackages = {"com.github.aleksikangas.backend"})
@EnableScheduling
@EntityScan(basePackages = {"com.github.aleksikangas.backend"})
public class BackendConfiguration {

}
