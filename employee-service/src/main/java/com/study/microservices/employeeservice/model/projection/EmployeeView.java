package com.study.microservices.employeeservice.model.projection;

import com.study.microservices.employeeservice.model.dto.EmployeeViewResponseDto;

import java.time.LocalDate;
import java.util.UUID;

public interface EmployeeView {
    UUID getId();

    String getName();

    String getSurname();

    LocalDate getBirthDate();

    default EmployeeViewResponseDto toEmployeeViewResponseDto() {
        return EmployeeViewResponseDto.builder()
                .id(getId())
                .name(getName())
                .surname(getSurname())
                .birthDate(getBirthDate())
                .build();
    }
}
