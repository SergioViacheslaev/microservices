package com.study.microservices.employeeservice.model.projection;

import com.study.microservices.employeeservice.model.dto.EmployeeMainInfoResponseDto;

import java.util.UUID;

public interface EmployeeView {
    UUID getId();

    String getName();

    String getSurname();

    default EmployeeMainInfoResponseDto toEmployeeMainInfoDto() {
        return EmployeeMainInfoResponseDto.builder()
                .id(getId())
                .name(getName())
                .surname(getSurname())
                .build();
    }
}
