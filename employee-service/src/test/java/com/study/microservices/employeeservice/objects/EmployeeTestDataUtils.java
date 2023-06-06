package com.study.microservices.employeeservice.objects;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class EmployeeTestDataUtils {

    public static EmployeeEntity createEmployeeEntity() {
        val employeeId = UUID.randomUUID();
        return EmployeeEntity.builder()
                .employeeId(employeeId)
                .employeeName("Ivan")
                .employeeSurname("Ivanov")
                .employeeBirthDate(LocalDate.parse("2007-12-03"))
                .employeePhones(List.of(EmployeePhoneEntity.builder()
                        .phoneNumber("+71234567890")
                        .build()))
                .build();
    }
}
