package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.dao.EmployeeEntityDao;
import com.study.microservices.employeeservice.exception.EmployeeFoundException;
import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeePhone;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
                .map(this::getEmployeeResponseDtoFromEntity)
                .toList();

        // with employeeRepository
       /* return employeeRepository.findAll().stream()
                .map(this::getEmployeeResponseDtoFromEntity)
                .toList();*/
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployeesByNameSortedByBirthDate(String employeeName, int page, int size) {
        val employeeResponseDtoList = employeeRepository.findAllByEmployeeNameOrderByEmployeeBirthDate(employeeName, PageRequest.of(page, size))
                .stream()
                .map(this::getEmployeeResponseDtoFromEntity)
                .toList();

        return new PageImpl<>(employeeResponseDtoList);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByNameAndSurname(String employeeName, String employeeSurname) {
        return employeeRepository.findByEmployeeNameAndEmployeeSurname(employeeName, employeeSurname)
                .map(this::getEmployeeResponseDtoFromEntity)
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

        final EmployeeEntity employeeEntityToSave = EmployeeEntity.builder()
                .employeeName(employeeName)
                .employeeSurname(employeeSurname)
                .employeeBirthDate(employeeCreateRequestDto.employeeBirthDate())
                .build();

        final List<EmployeePhoneEntity> employeePhoneEntitiesToSave = employeeCreateRequestDto.employeePhones().stream()
                .map(employeePhone -> EmployeePhoneEntity.builder()
                        .phoneNumber(employeePhone.phoneNumber())
                        .build())
                .toList();

        employeeEntityToSave.setEmployeePhones(employeePhoneEntitiesToSave);
        employeePhoneEntitiesToSave.forEach(employeePhoneEntity -> employeePhoneEntity.setEmployee(employeeEntityToSave));

        val savedEmployeeEntity = employeeRepository.save(employeeEntityToSave);
        val employeeResponseDto = getEmployeeResponseDtoFromEntity(savedEmployeeEntity);

        log.info("Saved EmployeeEntity {}", employeeResponseDto);

        return employeeResponseDto;
    }

    //todo: check if possible to get all employee phone numbers in single SQL
    @Transactional(readOnly = true)
    public EmployeeResponseDto findEmployeeByPhoneNumber(String phoneNumber) {
        val employeeEntity = employeeRepository.findByEmployeePhoneNumber(phoneNumber)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("Employee with such phone number %s doesn't exist", phoneNumber)));

        log.info("Found employeeEntity {}", employeeEntity);

        return getEmployeeResponseDtoFromEntity(employeeEntity);
    }

    private EmployeeResponseDto getEmployeeResponseDtoFromEntity(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .employeeId(employeeEntity.getEmployeeId())
                .employeeName(employeeEntity.getEmployeeName())
                .employeeSurname(employeeEntity.getEmployeeSurname())
                .employeeBirthDate(employeeEntity.getEmployeeBirthDate())
                .employeePhones(employeeEntity.getEmployeePhones().stream()
                        .map(employeePhoneEntity -> EmployeePhone.builder()
                                .phoneNumber(employeePhoneEntity.getPhoneNumber())
                                .build()).toList()
                )
                .build();
    }
}
