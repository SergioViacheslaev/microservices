package com.study.microservices.employeeservice.model.mapper;

import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponseDto toEmployeeResponseDto(EmployeeEntity employeeEntity);
}
