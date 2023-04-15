package com.study.microservices.employeeservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    public String getEmployeeName() {
        return "Bob";
    }
}
