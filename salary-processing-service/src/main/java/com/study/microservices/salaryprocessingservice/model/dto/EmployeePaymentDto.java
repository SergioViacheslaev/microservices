package com.study.microservices.salaryprocessingservice.model.dto;

import lombok.Builder;

@Builder
public record EmployeePaymentDto(
        String name,
        String sureName,
        String passportNumber,
        String monthlySalary,
        String payrollAccount) {
}
