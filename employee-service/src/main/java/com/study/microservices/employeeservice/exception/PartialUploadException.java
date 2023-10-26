package com.study.microservices.employeeservice.exception;

public class PartialUploadException extends RuntimeException {

    public PartialUploadException(String message) {
        super(message);
    }
}
