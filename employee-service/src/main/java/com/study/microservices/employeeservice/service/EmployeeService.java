package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.dao.EmployeeEntityDao;
import com.study.microservices.employeeservice.exception.EmployeeFoundException;
import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeePassport;
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
//        return employeeEntityDao.findAll().stream()
//                .map(this::getEmployeeResponseDtoFromEntity)
//                .toList();

        // with employeeRepository
        return employeeRepository.findAll().stream()
                .map(this::getEmployeeResponseDtoFromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployeesByNameSortedByBirthDate(String employeeName, int page, int size) {
        val employeeResponseDtoList = employeeRepository.findAllByNameOrderByBirthDate(employeeName, PageRequest.of(page, size))
                .stream()
                .map(this::getEmployeeResponseDtoFromEntity)
                .toList();

        return new PageImpl<>(employeeResponseDtoList);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByNameAndSurname(String employeeName, String employeeSurname) {
        return employeeRepository.findByNameAndSurname(employeeName, employeeSurname)
                .map(this::getEmployeeResponseDtoFromEntity)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("Employee with name %s and surname %s doesn't exist", employeeName, employeeSurname)));
    }

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto) {
        val employeeName = employeeCreateRequestDto.name();
        val employeeSurname = employeeCreateRequestDto.surname();
        employeeRepository.findByNameAndSurname(employeeName, employeeSurname)
                .ifPresent(employee -> {
                    throw new EmployeeFoundException(
                            String.format("Employee with name %s, surname %s already exists", employeeName, employeeSurname));
                });

        final EmployeeEntity employeeEntityToSave = EmployeeEntity.builder()
                .name(employeeName)
                .surname(employeeSurname)
                .birthDate(employeeCreateRequestDto.birthDate())
                .build();

        final List<EmployeePhoneEntity> employeePhoneEntitiesToSave = employeeCreateRequestDto.phones().stream()
                .map(employeePhone -> EmployeePhoneEntity.builder()
                        .phoneNumber(employeePhone.phoneNumber())
                        .build())
                .toList();

        employeeEntityToSave.setPhones(employeePhoneEntitiesToSave);
        employeePhoneEntitiesToSave.forEach(employeePhoneEntity -> employeePhoneEntity.setEmployee(employeeEntityToSave));

        val savedEmployeeEntity = employeeRepository.save(employeeEntityToSave);
        val employeeResponseDto = getEmployeeResponseDtoFromEntity(savedEmployeeEntity);

        log.info("Saved EmployeeEntity {}", employeeResponseDto);

        return employeeResponseDto;
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto findEmployeeByPhoneNumber(String phoneNumber) {
        val employeeEntity = employeeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        String.format("Employee with such phone number %s doesn't exist", phoneNumber)));

        log.info("Found employeeEntity {}", employeeEntity);

        return getEmployeeResponseDtoFromEntity(employeeEntity);
    }

    private EmployeeResponseDto getEmployeeResponseDtoFromEntity(EmployeeEntity employeeEntity) {
        return EmployeeResponseDto.builder()
                .Id(employeeEntity.getId())
                .name(employeeEntity.getName())
                .surname(employeeEntity.getSurname())
                .birthDate(employeeEntity.getBirthDate())
                .passport(EmployeePassport.builder()
                        .passportNumber(employeeEntity.getPassport().getPassportNumber())
                        .registrationAddress(employeeEntity.getPassport().getRegistrationAddress())
                        .build())
                .phones(employeeEntity.getPhones().stream()
                        .map(employeePhoneEntity -> EmployeePhone.builder()
                                .phoneNumber(employeePhoneEntity.getPhoneNumber())
                                .build()).toList()
                )
                .build();
    }
}
