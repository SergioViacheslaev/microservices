package com.study.microservices.employeeservice.exception;

public class EmployeePhoneFoundException extends RuntimeException {

    public EmployeePhoneFoundException(String message) {
        super(message);
    }
}
