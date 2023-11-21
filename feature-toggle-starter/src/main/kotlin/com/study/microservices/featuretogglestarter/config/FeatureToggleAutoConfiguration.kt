package com.study.microservices.featuretogglestarter.config

import com.study.microservices.featuretogglestarter.service.FeatureToggleService
import com.study.microservices.featuretogglestarter.service.impl.FeatureToggleServiceImpl
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("feature-toggles")
@Configuration
@EnableConfigurationProperties(FeatureToggleProperties::class)
class FeatureToggleAutoConfiguration {

    @Bean
    fun featureToggleService(featureToggleProperties: FeatureToggleProperties): FeatureToggleService {
        return FeatureToggleServiceImpl(featureToggleProperties)
    }

}