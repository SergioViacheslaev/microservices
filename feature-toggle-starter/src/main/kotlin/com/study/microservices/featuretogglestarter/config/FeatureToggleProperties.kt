package com.study.microservices.featuretogglestarter.config

import com.study.microservices.featuretogglestarter.model.FeatureToggle
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties should be in file application-feature-toggles.yml without any prefix, see example below.
 *
 * features:
 *   featureA:
 *     isEnabled: true
 *   featureB:
 *     isEnabled: false
 */
@Suppress("ConfigurationProperties") //properties should be without prefix
@ConfigurationProperties
data class FeatureToggleProperties(
    val features: Map<String, FeatureToggle>
)
