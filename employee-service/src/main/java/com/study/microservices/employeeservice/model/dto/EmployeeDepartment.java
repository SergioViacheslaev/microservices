package com.study.microservices.employeeservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmployeeDepartment(
        @NotBlank
        String departmentName) {
}
