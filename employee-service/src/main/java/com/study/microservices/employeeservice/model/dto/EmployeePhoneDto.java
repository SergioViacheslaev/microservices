package com.study.microservices.employeeservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmployeePhoneDto(
        @NotBlank(message = "Телефон сотрудника не может быть пустым")
        @Schema(defaultValue = "79214443322")
        String phoneNumber,

        @NotBlank(message = "Тип телефона не может быть пустым")
        @Schema(defaultValue = "Рабочий")
        String phoneType) {
}
