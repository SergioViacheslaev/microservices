package com.study.microservices.employeeservice.exception;

public class SseAsyncTimeoutException extends RuntimeException {

    public SseAsyncTimeoutException(String message) {
        super(message);
    }
}
