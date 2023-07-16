package com.study.microservices.employeeservice.objects_utils;

import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeePhone;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePassportEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class EmployeeTestDataUtils {

    public static EmployeeEntity createEmployeeEntity() {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .name("Alex")
                .surname("Ivanov")
                .birthDate(LocalDate.parse("2007-12-03"))
                .build();

        List<EmployeePhoneEntity> employeePhoneEntities = List.of(EmployeePhoneEntity.builder()
                .phoneNumber("71234567890")
                .build());

        EmployeePassportEntity employeePassportEntity = EmployeePassportEntity.builder()
                .passportNumber(123456780L)
                .registrationAddress("Улица Пушкина Дом 1")
                .build();

        employeeEntity.setPhones(employeePhoneEntities);
        employeeEntity.setPassport(employeePassportEntity);
        employeePassportEntity.setEmployee(employeeEntity);
        employeePhoneEntities.forEach(employeePhoneEntity -> employeePhoneEntity.setEmployee(employeeEntity));

        return employeeEntity;
    }

    public static EmployeeCreateRequestDto createEmployeeRequestDto() {
        return EmployeeCreateRequestDto.builder()
                .name("Foo")
                .surname("Bar")
                .birthDate(LocalDate.of(1987, 1, 15))
                .phones(List.of(EmployeePhone.builder()
                        .phoneNumber("71234567890")
                        .build()))
                .build();
    }

    public static EmployeeResponseDto createEmployeeResponseDto(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .Id(employeeEntity.getId())
                .name(employeeEntity.getName())
                .surname(employeeEntity.getSurname())
                .birthDate(employeeEntity.getBirthDate())
                .phones(employeeEntity.getPhones().stream()
                        .map(employeePhoneEntity -> EmployeePhone.builder()
                                .phoneNumber(employeePhoneEntity.getPhoneNumber())
                                .build()).toList()
                )
                .build();
    }
}
