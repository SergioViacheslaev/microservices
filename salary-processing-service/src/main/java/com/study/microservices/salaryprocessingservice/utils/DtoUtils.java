package com.study.microservices.salaryprocessingservice.utils;

import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeeResponseDto;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoUtils {

    public static List<EmployeePaymentDto> getEmployeePayments(List<EmployeeResponseDto> employees) {
        return employees.stream()
                .map(employee -> EmployeePaymentDto.builder()
                        .name(employee.name())
                        .sureName(employee.surname())
                        .passportNumber(employee.passport().passportNumber())
                        .monthlySalary(employee.monthlySalary())
                        .payrollAccount(employee.payrollAccount()).build()).toList();
    }

    public static EmployeePaymentDto getEmployeePaymentDummy() {
        return EmployeePaymentDto.builder()
                .name("Ivan")
                .sureName("Ivanov")
                .passportNumber("1234567890")
                .monthlySalary("100000")
                .payrollAccount("1234 1234567890")
                .build();
    }

}
