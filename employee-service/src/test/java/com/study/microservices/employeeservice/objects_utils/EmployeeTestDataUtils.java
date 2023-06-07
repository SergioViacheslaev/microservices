package com.study.microservices.employeeservice.objects_utils;

import com.study.microservices.employeeservice.model.dto.EmployeePhone;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class EmployeeTestDataUtils {

    public static EmployeeEntity createEmployeeEntity() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .employeeName("Alex")
                .employeeSurname("Ivanov")
                .employeeBirthDate(LocalDate.parse("2007-12-03"))
                .build();

        List<EmployeePhoneEntity> employeePhoneEntities = List.of(EmployeePhoneEntity.builder()
                .phoneNumber("+71234567890")
                .build());

        employeeEntity.setEmployeePhones(employeePhoneEntities);
        employeePhoneEntities.forEach(employeePhoneEntity -> employeePhoneEntity.setEmployee(employeeEntity));

        return employeeEntity;
    }

    public static EmployeeResponseDto createEmployeeResponseDto(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .employeeId(employeeEntity.getEmployeeId())
                .employeeName(employeeEntity.getEmployeeName())
                .employeeSurname(employeeEntity.getEmployeeSurname())
                .employeeBirthDate(employeeEntity.getEmployeeBirthDate())
                .employeePhones(employeeEntity.getEmployeePhones().stream()
                        .map(employeePhoneEntity -> EmployeePhone.builder()
                                .phoneNumber(employeePhoneEntity.getPhoneNumber())
                                .build()).toList()
                )
                .build();
    }
}
