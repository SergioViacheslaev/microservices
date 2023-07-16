package com.study.microservices.employeeservice.exception;

public class EmployeeBirthDateFormatException extends RuntimeException {

    public EmployeeBirthDateFormatException(String message) {
        super(message);
    }

    public EmployeeBirthDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
