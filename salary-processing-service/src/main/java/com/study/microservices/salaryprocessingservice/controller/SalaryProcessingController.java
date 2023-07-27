package com.study.microservices.salaryprocessingservice.controller;

import com.study.microservices.salaryprocessingservice.service.kafka.SalaryProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/salary-processing")
public class SalaryProcessingController {

    private final SalaryProcessingService salaryProcessingService;

    @Operation(summary = "Process Employees salary", description = "Processing Employees salary")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void processEmployeesSalary() {
        log.info("Received process employees salary request");
        salaryProcessingService.processEmployeesSalary();
        log.info("Processing employees salary completed");
    }

}
