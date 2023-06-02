package com.study.microservices.employeeservice.controller;

import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeCreateRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "Find all Employees", description = "Find all Employees description")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @Operation(summary = "Find all Employees by name sorted by birthDate")
    @GetMapping(path = "/{employeeName}")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployeesByName(@PathVariable String employeeName,
                                                                           @RequestParam Integer page,
                                                                           @RequestParam Integer size) {
        return new ResponseEntity<>(employeeService.getAllEmployeesByNameSortedByBirthDate(employeeName, page, size), HttpStatus.OK);
    }

    @GetMapping(path = "/filter")
    public ResponseEntity<EmployeeResponseDto> getEmployeeByNameAndSurname(@RequestParam String employeeName,
                                                                           @RequestParam String employeeSurname) {
        return new ResponseEntity<>(employeeService.getEmployeeByNameAndSurname(employeeName, employeeSurname), HttpStatus.OK);
    }
}
