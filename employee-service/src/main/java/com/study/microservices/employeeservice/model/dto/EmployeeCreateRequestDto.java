package com.study.microservices.employeeservice.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.study.microservices.employeeservice.utils.jackson.EmployeeBirthDateDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;


@Builder
public record EmployeeCreateRequestDto(

        @NotBlank(message = "Имя сотрудника должно быть заполнено")
        String name,

        @NotBlank(message = "Фамилия сотрудника должно быть заполнено")
        String surname,

        @Past(message = "Дата рождения должна быть в прошедшем времени")
        @JsonDeserialize(using = EmployeeBirthDateDeserializer.class)
        LocalDate birthDate,

        @NotNull
        @Size(min = 1, message = "Должен быть указан хотябы один телефон сотрудника")
        List<@Valid EmployeePhone> phones) {
}