package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @PostConstruct
    void init() {
        employeeRepository.save(EmployeeEntity.builder()
                .employeeId(UUID.randomUUID())
                .name("mops")
                .build());
    }

    public EmployeeDto getEmployeeByName(String name) {
        EmployeeEntity foundEmployeeEntity = employeeRepository.findByName(name)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("No Employee with name %s", name)));

        return EmployeeDto.builder()
                .id(foundEmployeeEntity.getEmployeeId())
                .name(foundEmployeeEntity.getName())
                .build();
    }
}
