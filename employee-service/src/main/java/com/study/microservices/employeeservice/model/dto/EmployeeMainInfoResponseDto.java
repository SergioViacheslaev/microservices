package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record EmployeeMainInfoResponseDto(
        UUID id,
        String name,
        String surname,
        LocalDate birthDate,
        String monthlySalary,
        String payrollAccount,
        EmployeePassport passport
) {
}
