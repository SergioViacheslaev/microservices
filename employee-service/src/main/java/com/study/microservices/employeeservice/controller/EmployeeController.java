package com.study.microservices.employeeservice.controller;

import com.study.microservices.employeeservice.model.dto.EmployeeDto;
import com.study.microservices.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<EmployeeDto> getEmployeeByName(@RequestParam String name) {
        return new ResponseEntity<>(employeeService.getEmployeeByName(name), HttpStatus.OK);
    }
}
