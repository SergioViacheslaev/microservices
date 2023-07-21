package com.study.microservices.salaryprocessingservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record EmployeePassport(

        @NotBlank
        @Size(min = 10, max = 10)
        @Schema(defaultValue = "1234567890")
        String passportNumber,

        @NotBlank
        String registrationAddress) {
}
