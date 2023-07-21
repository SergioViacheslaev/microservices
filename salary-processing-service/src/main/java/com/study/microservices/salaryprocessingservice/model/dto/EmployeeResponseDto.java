package com.study.microservices.salaryprocessingservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record EmployeeResponseDto(
        UUID Id,
        String name,
        String surname,
        LocalDate birthDate,
        EmployeePassport passport,
        String monthlySalary,
        String payrollAccount,
        List<EmployeePhoneDto> phones,
        List<EmployeeDepartment> departments) {
}
