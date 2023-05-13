package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record EmployeeCreateRequestDto(
        String employeeName,
        String employeeSurname,
        LocalDate employeeBirthDate) {
}