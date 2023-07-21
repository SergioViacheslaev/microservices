package com.study.microservices.salaryprocessingservice.service;

import com.study.microservices.salaryprocessingservice.api.EmployeeServiceApiClient;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalaryProcessingService {

    private final EmployeeServiceApiClient employeeServiceClient;

    public List<EmployeeResponseDto> processEmployeesSalary() {
        return employeeServiceClient.getAllEmployees();
    }
}
