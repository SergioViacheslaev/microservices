package com.study.microservices.employeeservice.objects;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class EmployeeTestDataUtils {

    public static EmployeeEntity createEmployeeEntity() {
        return EmployeeEntity.builder()
                .employeeId(UUID.randomUUID())
                .employeeName("Ivan")
                .employeeSurname("Ivanov")
                .employeeBirthDate(LocalDate.parse("2007-12-03"))
                .build();
    }
}
