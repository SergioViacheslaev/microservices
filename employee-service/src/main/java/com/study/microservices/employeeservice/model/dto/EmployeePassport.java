package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

@Builder
public record EmployeePassport(Long passportNumber, String registrationAddress) {
}
