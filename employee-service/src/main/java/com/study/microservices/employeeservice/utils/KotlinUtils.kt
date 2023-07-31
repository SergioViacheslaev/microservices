package com.study.microservices.employeeservice.utils

import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(object {}::class.java.`package`.name)

fun printKotlinCurrentVersion() = logger.info("Hello from Kotlin ${KotlinVersion.CURRENT}")
