package com.study.microservices.employeeservice.objects_utils;

import com.study.microservices.employeeservice.model.dto.*;
import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePassportEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class EmployeeObjectUtils {

    public static EmployeeEntity createEmployeeEntity() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .name("Alex")
                .surname("Ivanov")
                .birthDate(LocalDate.parse("2007-12-03"))
                .monthlySalary(BigDecimal.valueOf(100_000))
                .payrollAccount("1122 1234567890")
                .build();

        List<EmployeePhoneEntity> employeePhoneEntities = List.of(EmployeePhoneEntity.builder()
                .phoneNumber("71234567890")
                .phoneType(PhoneType.WORK)
                .build());

        EmployeePassportEntity employeePassportEntity = EmployeePassportEntity.builder()
                .passportNumber(123456780L)
                .registrationAddress("Улица Пушкина Дом 1")
                .build();

        EmployeeDepartmentEntity employeeDepartmentEntity = EmployeeDepartmentEntity.builder()
                .departmentName("АХО")
                .build();

        employeeEntity.addPassport(employeePassportEntity);
        employeeEntity.addDepartment(employeeDepartmentEntity);
        employeeEntity.addPhones(employeePhoneEntities);

        return employeeEntity;
    }

    public static EmployeeCreateRequestDto createEmployeeRequestDto() {
        return EmployeeCreateRequestDto.builder()
                .name("Foo")
                .surname("Bar")
                .birthDate(LocalDate.of(1987, 1, 15))
                .passport(EmployeePassport.builder()
                        .passportNumber("1234567890")
                        .registrationAddress("FooBar")
                        .build())
                .monthlySalary("150000")
                .payrollAccount("1234 1112223339")
                .phones(List.of(EmployeePhoneDto.builder()
                        .phoneNumber("71234567890")
                        .phoneType("Рабочий")
                        .build()))
                .departments(List.of(EmployeeDepartment.builder()
                        .departmentName("Управление автоматизации")
                        .build()))
                .build();
    }

    public static EmployeeResponseDto createEmployeeResponseDto(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .id(employeeEntity.getId())
                .name(employeeEntity.getName())
                .surname(employeeEntity.getSurname())
                .birthDate(employeeEntity.getBirthDate())
                .phones(employeeEntity.getPhones().stream()
                        .map(employeePhoneEntity -> EmployeePhoneDto.builder()
                                .phoneNumber(employeePhoneEntity.getPhoneNumber())
                                .build()).toList()
                )
                .build();
    }
}
