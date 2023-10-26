package com.study.microservices.employeeservice.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.study.microservices.employeeservice.util.jackson.EmployeeBirthDateDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;


@Builder
@Schema
public record EmployeeCreateRequestDto(

        @NotBlank(message = "Имя сотрудника должно быть заполнено")
        String name,

        @NotBlank(message = "Фамилия сотрудника должно быть заполнено")
        String surname,

        @Past(message = "Дата рождения должна быть в прошедшем времени")
        @NotNull
        @JsonDeserialize(using = EmployeeBirthDateDeserializer.class)
        @Schema(type = "string", format = "date", example = "1987-11-25")
        LocalDate birthDate,

        @NotNull
        @Valid
        EmployeePassport passport,

        @NotBlank(message = "Месячная зарплата")
        @Schema(type = "string", example = "125000")
        String monthlySalary,

        @NotBlank(message = "Счет зачисления зарплаты")
        @Schema(type = "string", example = "1234 1112223330")
        String payrollAccount,

        @NotNull
        List<@Valid EmployeeDepartment> departments,

        @NotNull
        @Size(min = 1, message = "Должен быть указан хотя бы один телефон сотрудника")
        List<@Valid EmployeePhoneDto> phones) {
}