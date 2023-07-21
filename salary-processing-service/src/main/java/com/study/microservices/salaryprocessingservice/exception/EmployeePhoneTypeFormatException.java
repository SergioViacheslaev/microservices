package com.study.microservices.salaryprocessingservice.exception;

public class EmployeePhoneTypeFormatException extends RuntimeException {

    public EmployeePhoneTypeFormatException(String message) {
        super(message);
    }
}
