package com.study.microservices.employeeservice.exception;


public class InvalidCsvException extends RuntimeException {

    public InvalidCsvException(String message) {
        super(message);
    }
}
