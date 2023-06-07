package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;


@Builder
public record EmployeeCreateRequestDto(
        String name,
        String surname,
        LocalDate birthDate,
        List<EmployeePhone> phones) {
}