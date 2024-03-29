package com.study.microservices.employeeservice.model.mapper;

import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.dto.EmployeeUpdateRequestDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.util.DtoUtils;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = DtoUtils.class)
public interface EmployeeMapper {

    @Mapping(target = "monthlySalary",
            expression = "java(DtoUtils.getFormattedSalary(employeeEntity.getMonthlySalary()))")
    EmployeeResponseDto toEmployeeResponseDto(EmployeeEntity employeeEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateEmployeeFromDto(EmployeeUpdateRequestDto employeeDto, @MappingTarget EmployeeEntity employeeEntity);

}
