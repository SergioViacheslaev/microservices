package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.exception.EmployeeDepartmentNotFoundException;
import com.study.microservices.employeeservice.exception.EmployeeFoundException;
import com.study.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.study.microservices.employeeservice.exception.EmployeePhoneFoundException;
import com.study.microservices.employeeservice.model.dto.EmployeeCreateRequestDto;
import com.study.microservices.employeeservice.model.dto.EmployeeMainInfoResponseDto;
import com.study.microservices.employeeservice.model.dto.EmployeePassport;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.dto.EmployeeUpdateRequestDto;
import com.study.microservices.employeeservice.model.dto.PhoneType;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePassportEntity;
import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import com.study.microservices.employeeservice.model.events.EmployeeEvent;
import com.study.microservices.employeeservice.model.events.EmployeeEventType;
import com.study.microservices.employeeservice.model.mapper.EmployeeMapper;
import com.study.microservices.employeeservice.repo.EmployeeDepartmentRepository;
import com.study.microservices.employeeservice.repo.EmployeePhoneRepository;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import com.study.microservices.employeeservice.util.DtoUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.study.microservices.employeeservice.util.DtoUtils.getEmployeeResponseDtoFromEntity;
import static com.study.microservices.employeeservice.util.SpecificationUtils.hasName;
import static com.study.microservices.employeeservice.util.SpecificationUtils.hasSurname;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ApplicationEventPublisher eventPublisher;
    private final EmployeeRepository employeeRepository;
    private final EmployeePhoneRepository employeePhoneRepository;
    private final EmployeeDepartmentRepository employeeDepartmentRepository;
    private final EmployeeMapper employeeMapper;
    private final EntityManager entityManager;

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto) {
        validateEmployeeCreateRequestDto(employeeCreateRequestDto);

        val employeeToCreate = EmployeeEntity.builder()
                .name(employeeCreateRequestDto.name())
                .surname(employeeCreateRequestDto.surname())
                .birthDate(employeeCreateRequestDto.birthDate())
                .monthlySalary(new BigDecimal(employeeCreateRequestDto.monthlySalary()))
                .payrollAccount(employeeCreateRequestDto.payrollAccount())
                .build();

        val employeePassportEntity = EmployeePassportEntity.builder()
                .passportNumber(Long.valueOf(employeeCreateRequestDto.passport().passportNumber()))
                .registrationAddress(employeeCreateRequestDto.passport().registrationAddress())
                .build();

        val employeePhoneEntities = employeeCreateRequestDto.phones().stream()
                .map(employeePhone -> EmployeePhoneEntity.builder()
                        .phoneNumber(employeePhone.phoneNumber())
                        .phoneType(PhoneType.getPhoneTypeFromString(employeePhone.phoneType()))
                        .build())
                .toList();

        employeeToCreate.addPassport(employeePassportEntity);
        employeeToCreate.addPhones(employeePhoneEntities);

        //fail creating new employee with non-existing department
        employeeCreateRequestDto.departments().forEach(departmentDto -> {
            val departmentEntity = employeeDepartmentRepository.findByDepartmentName(departmentDto.departmentName())
                    .orElseThrow(() -> new EmployeeDepartmentNotFoundException(
                            String.format("Department with name %s does not exist", departmentDto.departmentName())));
            employeeToCreate.addDepartment(departmentEntity);
        });

        val savedEmployeeEntity = employeeRepository.save(employeeToCreate);
        val employeeResponseDto = employeeMapper.toEmployeeResponseDto(savedEmployeeEntity);

        eventPublisher.publishEvent(new EmployeeEvent(EmployeeEventType.CREATE.getName(), employeeResponseDto.toString()));

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
    public void deleteEmployeeByPassportNumber(String passportNumber) {
        val employeeEntityToDelete = employeeRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with passport number %s not found", passportNumber)));

        employeeRepository.deleteById(employeeEntityToDelete.getId());

        eventPublisher.publishEvent(new EmployeeEvent(EmployeeEventType.DELETE.getName(),
                String.format("сотрудник с номером паспорта %s", passportNumber)));
    }

    @Transactional
    public void deleteEmployeeById(String employeeId) {
        try {
            //getReference returns initialized entity in transaction, not proxy
            val employeeEntityToDelete = entityManager.getReference(EmployeeEntity.class, UUID.fromString(employeeId));
            entityManager.remove(employeeEntityToDelete);
        } catch (EntityNotFoundException ex) {
            throw new EmployeeNotFoundException(String.format("Employee with id %s not found", employeeId));
        }

        eventPublisher.publishEvent(new EmployeeEvent(EmployeeEventType.DELETE.getName(),
                String.format("сотрудник с id %s", employeeId)));
    }

    @Transactional
    public void removeDepartmentFromEmployee(String employeeId, String departmentName) {
        val storedEmployee = employeeRepository.findById(UUID.fromString(employeeId))
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with id %s not found", employeeId)));
        val storedEmployeeDepartments = storedEmployee.getDepartments();
        val departmentEntityToRemove = storedEmployeeDepartments.stream()
                .filter(employeeDepartment -> departmentName.equals(employeeDepartment.getDepartmentName()))
                .findFirst()
                .orElseThrow(() -> new EmployeeDepartmentNotFoundException(String.format("Department with name %s is not set for employeeId %s",
                        departmentName, employeeId)));

        storedEmployeeDepartments.remove(departmentEntityToRemove);

        eventPublisher.publishEvent(new EmployeeEvent(EmployeeEventType.UPDATE.getName(),
                String.format("сотрудник с id %s, исключен из департамента %s", employeeId, departmentName)));
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        val employeeEntities = employeeRepository.findAllWithPassportAndPhones();

        return employeeEntities.stream()
                .map(employeeMapper::toEmployeeResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployeesBySurnameSortedByBirthDate(String employeeName, int page, int size) {
        val employeeResponseDtoList = employeeRepository.findAllBySurnameOrderByBirthDate(employeeName, PageRequest.of(page, size))
                .stream()
                .map(DtoUtils::getEmployeeResponseDtoFromEntity)
                .toList();

        return new PageImpl<>(employeeResponseDtoList);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByNameAndSurname(String employeeName, String employeeSurname) {
        return employeeRepository.findOne(where(hasName(employeeName)).and(where(hasSurname(employeeSurname))))
                .map(DtoUtils::getEmployeeResponseDtoFromEntity)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with name %s and surname %s doesn't exist", employeeName, employeeSurname)));
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto findEmployeeByPhoneNumber(String phoneNumber) {
        val employeeEntity = employeeRepository.findByPhones_PhoneNumber(phoneNumber)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with such phone number %s doesn't exist", phoneNumber)));

        log.info("Found employeeEntity {}", employeeEntity);

        return getEmployeeResponseDtoFromEntity(employeeEntity);
    }

    @Transactional(readOnly = true)
    public EmployeeMainInfoResponseDto findEmployeeById(String employeeId) {
        val employeeEntityOptional = employeeRepository.findById(UUID.fromString(employeeId));
        if (employeeEntityOptional.isEmpty()) {
            throw new EmployeeNotFoundException(String.format("Employee with such id %s not found", employeeId));
        }

        val employeeEntity = employeeEntityOptional.get();
        log.info("Found employeeEntity {}", employeeEntity);

        return EmployeeMainInfoResponseDto.builder()
                .id(employeeEntity.getId())
                .name(employeeEntity.getName())
                .surname(employeeEntity.getSurname())
                .birthDate(employeeEntity.getBirthDate())
                .payrollAccount(employeeEntity.getPayrollAccount())
                .monthlySalary(DtoUtils.getFormattedSalary(employeeEntity.getMonthlySalary()))
                .passport(EmployeePassport.builder()
                        .passportNumber(String.valueOf(employeeEntity.getPassport().getPassportNumber()))
                        .registrationAddress(employeeEntity.getPassport().getRegistrationAddress())
                        .build())
                .build();
    }

    private void validateEmployeeCreateRequestDto(EmployeeCreateRequestDto employeeCreateRequestDto) {
        val employeeName = employeeCreateRequestDto.name();
        val employeeSurname = employeeCreateRequestDto.surname();

        if (employeeRepository.existsEmployeeEntityByNameAndSurname(employeeName, employeeSurname)) {
            throw new EmployeeFoundException(String.format("Employee with name %s, surname %s already exists", employeeName, employeeSurname));
        }

        employeeCreateRequestDto.phones().forEach(phoneDto -> {
            if (employeePhoneRepository.existsByPhoneNumber(phoneDto.phoneNumber())) {
                throw new EmployeePhoneFoundException(String.format("This phone number %s is already exists", phoneDto.phoneNumber()));
            }
        });
    }

}

