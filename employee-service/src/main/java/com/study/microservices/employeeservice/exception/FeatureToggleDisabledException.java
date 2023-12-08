package com.study.microservices.employeeservice.exception;

public class FeatureToggleDisabledException extends RuntimeException {

    public FeatureToggleDisabledException(String message) {
        super(message);
    }
}
