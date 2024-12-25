package com.study.microservices.employeeservice.controller;

import com.study.microservices.employeeservice.exception.FeatureToggleDisabledException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.manager.FeatureManager;

import java.util.List;

import static com.study.microservices.employeeservice.config.FeatureToggles.GET_ALL_EMPLOYEES_FEATURE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {

    private final FeatureManager featureManager;
    private final EmployeeService employeeService;

    @Operation(summary = "Create new Employee", description = "Create new Employee with full info")
    @ResponseStatus(CREATED)
    @PostMapping
    public EmployeeResponseDto createEmployee(@Valid @RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        log.info("Received employeeCreateRequestDto {}", employeeCreateRequestDto);
        return employeeService.createEmployee(employeeCreateRequestDto);
    }

    @Operation(summary = "Update existing Employee", description = "Updates existing Employee, found by passport number")
    @PatchMapping(path = "/passport/{passport_number}")
    public EmployeeResponseDto updateEmployee(
            @Parameter(required = true) @PathVariable("passport_number") String passportNumber,
            @Valid @RequestBody EmployeeUpdateRequestDto employeeUpdateRequestDto) {
        log.info("Received employeeUpdateRequestDto {}", employeeUpdateRequestDto);
        return employeeService.updateEmployee(passportNumber, employeeUpdateRequestDto);
    }

    @Operation(summary = "Remove department from Employee", description = "Removes department from Employee")
    @PatchMapping(path = "/id/{employee_id}/departments/{department_name}")
    public void removeDepartmentFromEmployee(
            @Parameter(description = "employee's id", schema = @Schema(defaultValue = "b16355a9-3edf-418d-bafc-52e46f6703e1"))
            @PathVariable("employee_id") String employeeId,
            @Parameter(description = "employee's department to remove", schema = @Schema(defaultValue = "Управление автоматизации"))
            @PathVariable("department_name") String departmentName) {
        log.info("Received remove Department {} from Employee {} request", departmentName, employeeId);
        employeeService.removeDepartmentFromEmployee(employeeId, departmentName);
    }

    @Operation(summary = "Delete Employee by passport number", description = "Deletes Employee by passport number")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(path = "/passport/{passport_number}")
    public void deleteEmployeeByPassportNumber(
            @Parameter(description = "employee's passport number", schema = @Schema(defaultValue = "1234567891"))
            @PathVariable("passport_number") String passportNumber) {
        log.info("Received deleteEmployeeByPassportNumber {} request", passportNumber);
        employeeService.deleteEmployeeByPassportNumber(passportNumber);
    }

    @Operation(summary = "Delete Employee by id", description = "Deletes Employee by id")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(path = "/{employee_id}")
    public void deleteEmployeeById(
            @Parameter(description = "employee's id", schema = @Schema(defaultValue = "b16355a9-3edf-418d-bafc-52e46f6703e1"))
            @PathVariable("employee_id") String employeeId) {
        log.info("Received deleteEmployeeById {} request", employeeId);
        employeeService.deleteEmployeeById(employeeId);
    }

    @Operation(summary = "Find all Employees", description = "Finds all Employees")
    @GetMapping
    public List<EmployeeResponseDto> getAllEmployees() {
        if (featureManager.isActive(GET_ALL_EMPLOYEES_FEATURE)) {
            log.info("Received getAllEmployees request");
            return employeeService.getAllEmployees();
        } else {
            throw new FeatureToggleDisabledException(String.format("Feature toggle %s is disabled", GET_ALL_EMPLOYEES_FEATURE.name()));
        }
    }

    @Operation(summary = "Find Employee by id", description = "Get only main info about Employee")
    @GetMapping(path = "/{employeeId}")
    public EmployeeMainInfoResponseDto findEmployeeById(
            @Parameter(description = "Searched employee's id", schema = @Schema(defaultValue = "b16355a9-3edf-418d-bafc-52e46f6703e1"))
            @PathVariable String employeeId) {
        log.info("Received findEmployeeById {} request", employeeId);
        return employeeService.findEmployeeById(employeeId);
    }

    @Operation(summary = "Find all Employees by surname sorted by birthDate")
    @GetMapping(path = "/search/surname/{employeeSurname}")
    public Page<EmployeeResponseDto> getAllEmployeesByName(@PathVariable String employeeSurname,
                                                           @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "3") Integer size) {
        return employeeService.getAllEmployeesBySurnameSortedByBirthDate(employeeSurname, page, size);
    }

    @Operation(summary = "Find Employee by phone number", description = "Get all info about Employee by phone number")
    @GetMapping(path = "/search/phone/{phoneNumber}")
    public EmployeeResponseDto findEmployeeByPhoneNumber(
            @Parameter(description = "Searched employee's phone number", schema = @Schema(defaultValue = "792112345671"))
            @PathVariable String phoneNumber) {
        log.info("Received findEmployeeByPhoneNumber {} request", phoneNumber);
        return employeeService.findEmployeeByPhoneNumber(phoneNumber);
    }

    @Operation(summary = "Find Employee by name and surname", description = "Find Employee by name and surname")
    @GetMapping(path = "/search/name/{name}/surname/{surname}")
    public EmployeeResponseDto findEmployeeByNameAndSurname(@PathVariable("name") String employeeName,
                                                            @PathVariable("surname") String employeeSurname) {
        return employeeService.getEmployeeByNameAndSurname(employeeName, employeeSurname);
    }

}
