package com.study.microservices.employeeservice.service;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeServiceTest {

    @Test
    void getEmployeeName() {
        val employeeService = new EmployeeService();
        assertEquals("Bob", employeeService.getEmployeeName());
    }
}