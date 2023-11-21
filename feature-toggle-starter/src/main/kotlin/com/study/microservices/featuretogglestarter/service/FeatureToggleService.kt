package com.study.microservices.featuretogglestarter.service

interface FeatureToggleService {

    fun isEnabled(featureToggleName: String): Boolean
}