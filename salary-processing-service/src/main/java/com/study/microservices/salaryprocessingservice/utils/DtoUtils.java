package com.study.microservices.salaryprocessingservice.utils;

import com.study.microservices.salaryprocessingservice.model.dto.EmployeeDepartment;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeePassport;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeePhoneDto;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeeResponseDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;
import net.datafaker.Faker;

@UtilityClass
public class DtoUtils {

    private final Faker faker = new Faker();

    public static List<EmployeePaymentDto> getEmployeePayments(List<EmployeeResponseDto> employees) {
        return employees.stream()
                .map(employee -> EmployeePaymentDto.builder()
                        .name(employee.name())
                        .sureName(employee.surname())
                        .passportNumber(employee.passport().passportNumber())
                        .monthlySalary(employee.monthlySalary())
                        .payrollAccount(employee.payrollAccount()).build()).toList();
    }

    public static EmployeeResponseDto createRandomDummyEmployeeResponseDto() {
        return EmployeeResponseDto.builder()
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .birthDate(LocalDate.of(1987, 1, 15))
                .passport(EmployeePassport.builder()
                        .passportNumber(faker.passport().valid())
                        .registrationAddress(faker.address().streetAddress())
                        .build())
                .phones(List.of(EmployeePhoneDto.builder()
                        .phoneNumber(faker.phoneNumber().phoneNumber())
                        .phoneType("Рабочий")
                        .build()))
                .departments(List.of(EmployeeDepartment.builder()
                        .departmentName("Управление автоматизации")
                        .build()))
                .monthlySalary(String.valueOf(faker.number().numberBetween(100000,500000)))
                .payrollAccount(faker.idNumber().peselNumber())
                .build();
    }

}
