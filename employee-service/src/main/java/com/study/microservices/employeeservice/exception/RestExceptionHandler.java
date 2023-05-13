package com.study.microservices.employeeservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        log.warn("getEmployeeByName throws EmployeeNotFoundException: {}", ex.getMessage());
        return buildResponseEntity(new ApiErrorResponse(ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(EmployeeFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeFoundException(EmployeeFoundException ex) {
        log.warn("createEmployee throws EmployeeFoundException: {}", ex.getMessage());
        return buildResponseEntity(new ApiErrorResponse(ex.getMessage()), BAD_REQUEST);
    }

    private ResponseEntity<ApiErrorResponse> buildResponseEntity(ApiErrorResponse apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }

    private record ApiErrorResponse(String message) {
    }

}
