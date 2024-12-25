package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record EmployeeViewResponseDto(
        UUID id,
        String name,
        String surname,
        LocalDate birthDate
) {
}
