package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeDto(UUID id, String name) {
}
