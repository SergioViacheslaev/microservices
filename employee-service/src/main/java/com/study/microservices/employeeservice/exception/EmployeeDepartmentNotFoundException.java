package com.study.microservices.employeeservice.exception;

public class EmployeeDepartmentNotFoundException extends RuntimeException {

    public EmployeeDepartmentNotFoundException(String message) {
        super(message);
    }

    public EmployeeDepartmentNotFoundException(Throwable cause) {
        super(cause);
    }
}
