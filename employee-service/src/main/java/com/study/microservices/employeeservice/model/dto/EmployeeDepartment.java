package com.study.microservices.employeeservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmployeeDepartment(
        @NotBlank
        @Schema(defaultValue = "Департамент разработки ПО")
        String departmentName) {
}
