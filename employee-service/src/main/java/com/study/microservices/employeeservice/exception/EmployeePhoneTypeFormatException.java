package com.study.microservices.employeeservice.exception;

public class EmployeePhoneTypeFormatException extends RuntimeException {

    public EmployeePhoneTypeFormatException(String message) {
        super(message);
    }
}
