package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.exception.EmployeeFoundException;
import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.exception.EmployeePhoneFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.dto.EmployeeUpdateRequestDto;
import com.study.microservices.employeeservice.model.dto.PhoneType;
import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePassportEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import com.study.microservices.employeeservice.model.mapper.EmployeeMapper;
import com.study.microservices.employeeservice.repo.EmployeeDepartmentRepository;
import com.study.microservices.employeeservice.repo.EmployeePhoneRepository;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import com.study.microservices.employeeservice.utils.DtoUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.study.microservices.employeeservice.utils.DtoUtils.getEmployeeResponseDtoFromEntity;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeePhoneRepository employeePhoneRepository;
    private final EmployeeDepartmentRepository employeeDepartmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeePhoneRepository employeePhoneRepository,
                           EmployeeDepartmentRepository employeeDepartmentRepository,
                           EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeePhoneRepository = employeePhoneRepository;
        this.employeeDepartmentRepository = employeeDepartmentRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto) {
        validateEmployeeCreateRequestDto(employeeCreateRequestDto);

        final EmployeeEntity employeeToSave = EmployeeEntity.builder()
                .name(employeeCreateRequestDto.name())
                .surname(employeeCreateRequestDto.surname())
                .birthDate(employeeCreateRequestDto.birthDate())
                .build();

        final EmployeePassportEntity employeePassportEntity = EmployeePassportEntity.builder()
                .passportNumber(Long.valueOf(employeeCreateRequestDto.passport().passportNumber()))
                .registrationAddress(employeeCreateRequestDto.passport().registrationAddress())
                .build();

        final List<EmployeePhoneEntity> employeePhonesToSave = employeeCreateRequestDto.phones().stream()
                .map(employeePhone -> EmployeePhoneEntity.builder()
                        .phoneNumber(employeePhone.phoneNumber())
                        .phoneType(PhoneType.getPhoneTypeFromString(employeePhone.phoneType()))
                        .build())
                .toList();

        //Set existing department or save new and set it
        val employeeDepartmentsToSave = new ArrayList<EmployeeDepartmentEntity>();
        employeeCreateRequestDto.departments().forEach(dtoDepartment -> {
            Optional<EmployeeDepartmentEntity> departmentEntity = employeeDepartmentRepository.findByDepartmentName(dtoDepartment.departmentName());
            departmentEntity.ifPresent(employeeDepartmentsToSave::add);
            if (departmentEntity.isEmpty()) {
                employeeDepartmentsToSave.add(EmployeeDepartmentEntity.builder()
                        .departmentName(dtoDepartment.departmentName())
                        .build());
            }
        });

        employeeToSave.addPassport(employeePassportEntity);
        employeeToSave.addPhones(employeePhonesToSave);
        employeeDepartmentsToSave.forEach(employeeToSave::addDepartment);

        val savedEmployeeEntity = employeeRepository.save(employeeToSave);
        val employeeResponseDto = employeeMapper.toEmployeeResponseDto(savedEmployeeEntity);

        log.info("Created EmployeeEntity {}", employeeResponseDto);

        return employeeResponseDto;
    }

    @Transactional
    public EmployeeResponseDto updateEmployee(String passportNumber, EmployeeUpdateRequestDto employeeUpdateRequestDto) {
        val employeeEntityToUpdate = employeeRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with passport number %s not found", passportNumber)));

        employeeMapper.updateEmployeeFromDto(employeeUpdateRequestDto, employeeEntityToUpdate);

        val updatedEmployeeEntity = employeeRepository.save(employeeEntityToUpdate);
        val employeeResponseDto = employeeMapper.toEmployeeResponseDto(updatedEmployeeEntity);

        log.info("Updated EmployeeEntity {}", employeeResponseDto);

        return employeeResponseDto;
    }

    @Transactional
    public void deleteEmployee(String passportNumber) {
        val employeeEntityToDelete = employeeRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with passport number %s not found", passportNumber)));

        employeeRepository.deleteById(employeeEntityToDelete.getId());

        log.info("Deleted EmployeeEntity with Id: {}", employeeEntityToDelete.getId());
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        val employeeEntities = employeeRepository.findAllWithPassportAndPhones();

        return employeeEntities.stream()
                .map(employeeMapper::toEmployeeResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployeesByNameSortedByBirthDate(String employeeName, int page, int size) {
        val employeeResponseDtoList = employeeRepository.findAllByNameOrderByBirthDate(employeeName, PageRequest.of(page, size))
                .stream()
                .map(DtoUtils::getEmployeeResponseDtoFromEntity)
                .toList();

        return new PageImpl<>(employeeResponseDtoList);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByNameAndSurname(String employeeName, String employeeSurname) {
        return employeeRepository.findByNameAndSurname(employeeName, employeeSurname)
                .map(DtoUtils::getEmployeeResponseDtoFromEntity)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with name %s and surname %s doesn't exist", employeeName, employeeSurname)));
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto findEmployeeByPhoneNumber(String phoneNumber) {
        val employeeEntity = employeeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with such phone number %s doesn't exist", phoneNumber)));

        log.info("Found employeeEntity {}", employeeEntity);

        return getEmployeeResponseDtoFromEntity(employeeEntity);
    }

    private void validateEmployeeCreateRequestDto(EmployeeCreateRequestDto employeeCreateRequestDto) {
        val employeeName = employeeCreateRequestDto.name();
        val employeeSurname = employeeCreateRequestDto.surname();

        employeeRepository.findByNameAndSurname(employeeName, employeeSurname)
                .ifPresent(employee -> {
                    throw new EmployeeFoundException(String.format("Employee with name %s, surname %s already exists", employeeName, employeeSurname));
                });

        employeeCreateRequestDto.phones().forEach(phoneDto -> {
            PhoneType.getPhoneTypeFromString(phoneDto.phoneType());
            if (employeePhoneRepository.existsByPhoneNumber(phoneDto.phoneNumber())) {
                throw new EmployeePhoneFoundException(String.format("This phone number %s is already exists", phoneDto.phoneNumber()));
            }
        });
    }
}
