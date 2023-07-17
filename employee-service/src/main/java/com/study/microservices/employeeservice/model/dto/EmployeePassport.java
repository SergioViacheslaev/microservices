package com.study.microservices.employeeservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record EmployeePassport(

        @NotBlank
        @Size(min = 10, max = 10)
        String passportNumber,

        @NotBlank
        String registrationAddress) {
}
