package com.study.microservices.employeeservice.controller;

import com.study.microservices.employeeservice.model.dto.EmployeeViewResponseDto;
import com.study.microservices.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/employees")
public class EmployeeViewController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get Employee view", description = "Get Employee view")
    @GetMapping(path = "/{employeeId}/view")
    public EmployeeViewResponseDto getEmployeeViewById(
            @Parameter(description = "Searched employee's id", schema = @Schema(defaultValue = "b16355a9-3edf-418d-bafc-52e46f6703e1"))
            @PathVariable String employeeId) {
        log.info("Received getEmployeeViewById {} request", employeeId);
        return employeeService.getEmployeeViewById(employeeId);
    }
}
