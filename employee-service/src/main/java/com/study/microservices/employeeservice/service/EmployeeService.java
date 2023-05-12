package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDto getEmployeeByName(String name) {
        EmployeeEntity foundEmployeeEntity = employeeRepository.findByEmployeeName(name)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("No Employee with name %s", name)));

        return EmployeeDto.builder()
                .id(foundEmployeeEntity.getEmployeeId())
                .name(foundEmployeeEntity.getEmployeeName())
                .build();
    }
}
