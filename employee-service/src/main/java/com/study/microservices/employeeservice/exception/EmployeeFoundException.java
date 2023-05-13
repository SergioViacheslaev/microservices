package com.study.microservices.employeeservice.exception;

public class EmployeeFoundException extends RuntimeException {

    public EmployeeFoundException(String message) {
        super(message);
    }

    public EmployeeFoundException(Throwable cause) {
        super(cause);
    }
}
