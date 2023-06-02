package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.dao.EmployeeEntityDao;
import com.study.microservices.employeeservice.exception.EmployeeFoundException;
import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeEntityDao employeeEntityDao;

    public EmployeeService(EmployeeRepository employeeRepository,
//                           @Qualifier(value = "employeeEntityJdbcTemplateDao")
//                           @Qualifier(value = "employeeEntityHibernateDao")
                           EmployeeEntityDao employeeEntityDao) {
        this.employeeRepository = employeeRepository;
        this.employeeEntityDao = employeeEntityDao;
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        // with DAO
        return employeeEntityDao.findAll().stream()
                .map(employeeEntity -> EmployeeResponseDto.builder()
                        .employeeId(employeeEntity.getEmployeeId())
                        .employeeName(employeeEntity.getEmployeeName())
                        .employeeSurname(employeeEntity.getEmployeeSurname())
                        .employeeBirthDate(employeeEntity.getEmployeeBirthDate())
                        .build())
                .toList();

        // with employeeRepository
       /* return employeeRepository.findAll().stream()
                .map(employeeEntity -> EmployeeResponseDto.builder()
                        .employeeId(employeeEntity.getEmployeeId())
                        .employeeName(employeeEntity.getEmployeeName())
                        .employeeSurname(employeeEntity.getEmployeeSurname())
                        .employeeBirthDate(employeeEntity.getEmployeeBirthDate())
                        .build())
                .toList();*/
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployeesByNameSortedByBirthDate(String employeeName, int page, int size) {
        val employeeResponseDtoList = employeeRepository.findAllByEmployeeNameOrderByEmployeeBirthDate(employeeName, PageRequest.of(page, size)).stream()
                .map(employeeEntity -> EmployeeResponseDto.builder()
                        .employeeId(employeeEntity.getEmployeeId())
                        .employeeName(employeeEntity.getEmployeeName())
                        .employeeSurname(employeeEntity.getEmployeeSurname())
                        .employeeBirthDate(employeeEntity.getEmployeeBirthDate())
                        .build())
                .toList();

        return new PageImpl<>(employeeResponseDtoList);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByNameAndSurname(String employeeName, String employeeSurname) {
        return employeeRepository.findByEmployeeNameAndEmployeeSurname(employeeName, employeeSurname)
                .map(employeeEntity -> EmployeeResponseDto.builder()
                        .employeeId(employeeEntity.getEmployeeId())
                        .employeeName(employeeEntity.getEmployeeName())
                        .employeeSurname(employeeEntity.getEmployeeSurname())
                        .employeeBirthDate(employeeEntity.getEmployeeBirthDate())
                        .build())
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("Employee with name %s and surname %s doesn't exist", employeeName, employeeSurname)));
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
