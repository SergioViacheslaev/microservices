package com.study.microservices.featuretogglestarter.service.impl

import com.study.microservices.featuretogglestarter.config.FeatureToggleProperties
import com.study.microservices.featuretogglestarter.service.FeatureToggleService
import org.slf4j.LoggerFactory


class FeatureToggleServiceImpl(
    private val featureToggleProperties: FeatureToggleProperties
) : FeatureToggleService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun isEnabled(featureToggleName: String): Boolean {
        val featureToggle =
            featureToggleProperties.features[featureToggleName] ?: throw IllegalArgumentException("Нет featureToggle с названием $featureToggleName")

        log.info("featureToggle $featureToggleName ${if (featureToggle.isEnabled) "включена" else "выключена"}")
        return featureToggle.isEnabled
    }
}