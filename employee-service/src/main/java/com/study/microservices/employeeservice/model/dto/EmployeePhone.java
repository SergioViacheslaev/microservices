package com.study.microservices.employeeservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmployeePhone(
        @NotBlank(message = "Телефон сотрудника не может быть пустым")
        String phoneNumber,

        PhoneType phoneType) {
}
