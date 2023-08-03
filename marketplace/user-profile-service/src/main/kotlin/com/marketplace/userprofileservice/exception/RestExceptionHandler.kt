package com.marketplace.userprofileservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(UserDataNotFoundException::class)
    fun handleUserDataNotFoundException(ex: UserDataNotFoundException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity(ApiErrorResponse(ex.message!!), HttpStatus.NOT_FOUND)
    }

    data class ApiErrorResponse(val message: String)

}