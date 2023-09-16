package com.study.microservices.employeeservice.exception;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @ExceptionHandler(EmployeeDepartmentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeDepartmentNotFoundException(EmployeeDepartmentNotFoundException ex) {
        log.warn("getDepartmentToRemove throws EmployeeNotFoundException: {}", ex.getMessage());
        return buildResponseEntity(new ApiErrorResponse(ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(EmployeeFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeeFoundException(EmployeeFoundException ex) {
        log.warn("createEmployee throws EmployeeFoundException: {}", ex.getMessage());
        return buildResponseEntity(new ApiErrorResponse(ex.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(EmployeePhoneFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEmployeePhoneFoundException(EmployeePhoneFoundException ex) {
        log.warn("createEmployee throws EmployeePhoneFoundException: {}", ex.getMessage());
        return buildResponseEntity(new ApiErrorResponse(ex.getMessage()), BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmployeeBirthDateFormatException.class)
    public ApiDtoValidationExceptionResponse handleEmployeeBirthDateFormatException(EmployeeBirthDateFormatException ex) {
        log.warn("Employee birthDate validation exception: {}", ex.getMessage());
        return new ApiDtoValidationExceptionResponse("Invalid request data", Map.of("birthDate", ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmployeePhoneTypeFormatException.class)
    public ApiDtoValidationExceptionResponse handleEmployeePhoneTypeFormatException(EmployeePhoneTypeFormatException ex) {
        log.warn("Employee phone type validation exception: {}", ex.getMessage());
        return new ApiDtoValidationExceptionResponse("Invalid request data", Map.of("phoneType", ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiDtoValidationExceptionResponse handleRequestDtoValidationExceptions(MethodArgumentNotValidException ex) {
        val validationErrors = new HashMap<String, String>();
        val requestDtoClassName = Objects.requireNonNull(ex.getTarget()).getClass().getSimpleName();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        log.warn("Validation failed for {}, invalid fields: {}", requestDtoClassName, validationErrors);

        return new ApiDtoValidationExceptionResponse("Invalid request data", validationErrors);
    }

    private ResponseEntity<ApiErrorResponse> buildResponseEntity(ApiErrorResponse apiError, HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }

    private record ApiErrorResponse(String message) {
    }

    private record ApiDtoValidationExceptionResponse(String message, Map<String, String> validationErrors) {
    }

}
