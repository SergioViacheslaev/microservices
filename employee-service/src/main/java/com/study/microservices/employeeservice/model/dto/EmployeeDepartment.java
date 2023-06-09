package com.study.microservices.employeeservice.model.dto;

import lombok.Builder;

@Builder
public record EmployeeDepartment(String departmentName) {
}
