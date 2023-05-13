package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.exception.EmployeeFoundException;
import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByNameAndSurname(String employeeName, String employeeSurname) {
        EmployeeEntity foundEmployeeEntity = employeeRepository.findByEmployeeNameAndEmployeeSurname(employeeName, employeeSurname)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("No Employee with these name or surname %s %s", employeeName, employeeSurname)));

        return EmployeeResponseDto.builder()
                .employeeId(foundEmployeeEntity.getEmployeeId())
                .employeeName(foundEmployeeEntity.getEmployeeName())
                .employeeSurname(foundEmployeeEntity.getEmployeeSurname())
                .employeeBirthDate(foundEmployeeEntity.getEmployeeBirthDate())
                .build();
    }

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto) {
        val employeeName = employeeCreateRequestDto.employeeName();
        val employeeSurname = employeeCreateRequestDto.employeeSurname();
        employeeRepository.findByEmployeeNameAndEmployeeSurname(employeeName, employeeSurname)
                .ifPresent(employee -> {
                    throw new EmployeeFoundException(
                            String.format("Employee with name %s, surname %s already exists", employeeName, employeeSurname));
                });

        EmployeeEntity createdEmployeeEntity = employeeRepository.save(EmployeeEntity.builder()
                .employeeName(employeeName)
                .employeeSurname(employeeSurname)
                .employeeBirthDate(employeeCreateRequestDto.employeeBirthDate())
                .build());

        return EmployeeResponseDto.builder()
                .employeeId(createdEmployeeEntity.getEmployeeId())
                .employeeName(createdEmployeeEntity.getEmployeeName())
                .employeeSurname(createdEmployeeEntity.getEmployeeSurname())
                .employeeBirthDate(createdEmployeeEntity.getEmployeeBirthDate())
                .build();

    }
}
