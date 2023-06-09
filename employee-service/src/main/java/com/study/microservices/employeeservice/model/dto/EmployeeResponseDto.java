package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record EmployeeResponseDto(
        UUID Id,
        String name,
        String surname,
        LocalDate birthDate,
        EmployeePassport passport,
        List<EmployeePhone> phones,
        Set<EmployeeDepartment> departments) {
}
