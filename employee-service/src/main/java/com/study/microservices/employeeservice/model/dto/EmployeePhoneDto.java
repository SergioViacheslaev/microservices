package com.study.microservices.employeeservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmployeePhoneDto(
        @NotBlank(message = "Телефон сотрудника не может быть пустым")
        String phoneNumber,

        @NotBlank(message = "Тип телефона не может быть пустым")
        String phoneType) {
}
