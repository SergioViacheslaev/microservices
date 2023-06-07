package com.study.microservices.employeeservice.controller;

import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create new Employee", description = "Create new Employee with phones")
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        log.info("Received createEmployee requestDto {}", employeeCreateRequestDto);
        return new ResponseEntity<>(employeeService.createEmployee(employeeCreateRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Find all Employees", description = "Find all Employees description")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        log.info("Received getAllEmployees request");
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @Operation(summary = "Find all Employees by name sorted by birthDate")
    @GetMapping(path = "/{employeeName}")
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployeesByName(@PathVariable String employeeName,
                                                                           @RequestParam Integer page,
                                                                           @RequestParam Integer size) {
        return new ResponseEntity<>(employeeService.getAllEmployeesByNameSortedByBirthDate(employeeName, page, size), HttpStatus.OK);
    }

    @Operation(summary = "Find Employee by filter", description = "Find Employee by name and surname")
    @GetMapping(path = "/filter")
    public ResponseEntity<EmployeeResponseDto> findEmployeeByNameAndSurname(@RequestParam String employeeName,
                                                                            @RequestParam String employeeSurname) {
        return new ResponseEntity<>(employeeService.getEmployeeByNameAndSurname(employeeName, employeeSurname), HttpStatus.OK);
    }

    @Operation(summary = "Find Employee by phone number", description = "Find Employee by phone number")
    @GetMapping(path = "/phones/{phoneNumber}")
    public ResponseEntity<EmployeeResponseDto> findEmployeeByPhoneNumber(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(employeeService.findEmployeeByPhoneNumber(phoneNumber), HttpStatus.OK);
    }
}
