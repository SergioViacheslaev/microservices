package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

@Builder
public record EmployeePhone(String phoneNumber, PhoneType phoneType) {
}
