package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record EmployeeResponseDto(
        UUID employeeId,
        String employeeName,
        String employeeSurname,
        LocalDate employeeBirthDate,
        List<EmployeePhone> employeePhones) {
}
