package com.study.microservices.employeeservice.utils;

import com.study.microservices.employeeservice.model.dto.EmployeeDepartment;
import com.study.microservices.employeeservice.model.dto.EmployeePassport;
import com.study.microservices.employeeservice.model.dto.EmployeePhoneDto;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@UtilityClass
public class DtoUtils {

    public static EmployeeResponseDto getEmployeeResponseDtoFromEntity(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .id(employeeEntity.getId())
                .name(employeeEntity.getName())
                .surname(employeeEntity.getSurname())
                .birthDate(employeeEntity.getBirthDate())
                .monthlySalary(getFormattedSalary(employeeEntity.getMonthlySalary()))
                .payrollAccount(employeeEntity.getPayrollAccount())
                .passport(EmployeePassport.builder()
                        .passportNumber(String.valueOf(employeeEntity.getPassport().getPassportNumber()))
                        .registrationAddress(employeeEntity.getPassport().getRegistrationAddress())
                        .build())
                .phones(employeeEntity.getPhones().stream()
                        .map(employeePhoneEntity -> EmployeePhoneDto.builder()
                                .phoneNumber(employeePhoneEntity.getPhoneNumber())
                                .phoneType(employeePhoneEntity.getPhoneType().getName())
                                .build()).toList()
                )
                .departments(employeeEntity.getDepartments().stream()
                        .map(employeeDepartmentEntity -> EmployeeDepartment.builder()
                                .departmentName(employeeDepartmentEntity.getDepartmentName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static String getFormattedSalary(BigDecimal monthlySalary) {
        String salary = monthlySalary.toPlainString();
        if (salary.endsWith(".000")) {
            return salary.substring(0, salary.indexOf(".000"));
        } else {
            return salary;
        }
    }
}
