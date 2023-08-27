package com.study.microservices.employeeservice.integration.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class IntegrationTestConfiguration {

    @Bean
    public SpringContextUtils springContextUtils(ApplicationContext applicationContext) {
        return new SpringContextUtils(applicationContext);
    }
}
