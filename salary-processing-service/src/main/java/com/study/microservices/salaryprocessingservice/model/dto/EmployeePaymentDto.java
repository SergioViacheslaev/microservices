package com.study.microservices.salaryprocessingservice.model.dto;

import com.study.microservices.salaryprocessingservice.model.entity.EmployeePaymentEntity;
import lombok.Builder;

@Builder
public record EmployeePaymentDto(
        String paymentId,
        String name,
        String sureName,
        String passportNumber,
        String monthlySalary,
        String payrollAccount) {

    public EmployeePaymentEntity toEntity() {
        return EmployeePaymentEntity.builder()
                .paymentId(paymentId)
                .name(name)
                .sureName(sureName)
                .passportNumber(passportNumber)
                .monthlySalary(monthlySalary)
                .payrollAccount(payrollAccount)
                .build();
    }
}
