package com.study.microservices.employeeservice.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.study.microservices.employeeservice.utils.jackson.EmployeeBirthDateDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;


@Builder
@Schema
public record EmployeeUpdateRequestDto(
        String name,
        String surname,
        @JsonDeserialize(using = EmployeeBirthDateDeserializer.class)
        LocalDate birthDate
) {

}

