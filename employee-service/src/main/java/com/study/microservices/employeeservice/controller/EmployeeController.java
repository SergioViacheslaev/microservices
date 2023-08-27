package com.study.microservices.employeeservice.controller;

import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeeMainInfoResponseDto;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.dto.EmployeeUpdateRequestDto;
import com.study.microservices.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create new Employee", description = "Create new Employee with phones")
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        log.info("Received employeeCreateRequestDto {}", employeeCreateRequestDto);
        return new ResponseEntity<>(employeeService.createEmployee(employeeCreateRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing Employee", description = "Updates existing Employee, found by passport number")
    @PatchMapping(path = "/{passport_number}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @Parameter(required = true)
            @PathVariable("passport_number")
            String passportNumber,
            @Valid @RequestBody EmployeeUpdateRequestDto employeeUpdateRequestDto) {
        log.info("Received employeeUpdateRequestDto {}", employeeUpdateRequestDto);
        return new ResponseEntity<>(employeeService.updateEmployee(passportNumber, employeeUpdateRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete Employee by passport number", description = "Deletes Employee by passport number")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{passport_number}")
    public void deleteEmployeeByPassportNumber(
            @Parameter(description = "employee's passport number", schema = @Schema(defaultValue = "1234567891"))
            @PathVariable("passport_number")
            String passportNumber) {
        log.info("Received deleteEmployeeByPassportNumber {} request", passportNumber);
        employeeService.deleteEmployeeByPassportNumber(passportNumber);
    }

    @Operation(summary = "Delete Employee by id", description = "Deletes Employee by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/id/{employee_id}")
    public void deleteEmployeeById(
            @Parameter(description = "employee's id", schema = @Schema(defaultValue = "b16355a9-3edf-418d-bafc-52e46f6703e1"))
            @PathVariable("employee_id")
            String employeeId) {
        log.info("Received deleteEmployeeById {} request", employeeId);
        employeeService.deleteEmployeeById(employeeId);
    }

    @Operation(summary = "Find all Employees", description = "Finds all Employees")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        log.info("Received getAllEmployees request");
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @Operation(summary = "Find all Employees by surname sorted by birthDate")
    @GetMapping(path = "/{employeeSurname}")
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployeesByName(@PathVariable String employeeSurname,
                                                                           @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                           @RequestParam(value = "size", defaultValue = "3") Integer size) {
        return new ResponseEntity<>(employeeService.getAllEmployeesBySurnameSortedByBirthDate(employeeSurname, page, size), HttpStatus.OK);
    }

    @Operation(summary = "Find Employee by id", description = "Get only Employee entity main info")
    @GetMapping(path = "/id/{employeeId}")
    public ResponseEntity<EmployeeMainInfoResponseDto> findEmployeeById(
            @Parameter(description = "Searched employee's id", schema = @Schema(defaultValue = "b16355a9-3edf-418d-bafc-52e46f6703e1"))
            @PathVariable String employeeId) {
        log.info("Received findEmployeeById {} request", employeeId);
        return new ResponseEntity<>(employeeService.findEmployeeById(employeeId), HttpStatus.OK);
    }

    @Operation(summary = "Find Employee by filter", description = "Find Employee by name and surname")
    @GetMapping(path = "/filter")
    public ResponseEntity<EmployeeResponseDto> findEmployeeByNameAndSurname(@RequestParam String employeeName,
                                                                            @RequestParam String employeeSurname) {
        return new ResponseEntity<>(employeeService.getEmployeeByNameAndSurname(employeeName, employeeSurname), HttpStatus.OK);
    }

    @Operation(summary = "Find Employee by phone number", description = "Find Employee by phone number")
    @GetMapping(path = "/phones/{phoneNumber}")
    public ResponseEntity<EmployeeResponseDto> findEmployeeByPhoneNumber(
            @Parameter(description = "Searched employee's phone number", schema = @Schema(defaultValue = "792112345671"))
            @PathVariable String phoneNumber) {
        log.info("Received findEmployeeByPhoneNumber {} request", phoneNumber);
        return new ResponseEntity<>(employeeService.findEmployeeByPhoneNumber(phoneNumber), HttpStatus.OK);
    }
}
