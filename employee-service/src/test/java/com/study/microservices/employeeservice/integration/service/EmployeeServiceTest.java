package com.study.microservices.employeeservice.integration.service;

import com.study.microservices.employeeservice.exception.EmployeeDepartmentNotFoundException;
import com.study.microservices.employeeservice.integration.config.IntegrationTestConfiguration;
import com.study.microservices.employeeservice.integration.config.SpringContextUtils;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.mapper.EmployeeMapperImpl;
import com.study.microservices.employeeservice.objects_utils.EmployeeObjectUtils;
import com.study.microservices.employeeservice.repo.EmployeeDepartmentRepository;
import com.study.microservices.employeeservice.repo.EmployeePhoneRepository;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import com.study.microservices.employeeservice.service.EmployeeService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        EmployeeService.class,
        EmployeeMapperImpl.class,
        EntityManager.class,
        ApplicationContext.class
})
@Import(IntegrationTestConfiguration.class)
class EmployeeServiceTest {

    @Autowired
    SpringContextUtils springContextUtils;

    @Autowired
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    EmployeePhoneRepository phoneRepository;

    @MockBean
    EmployeeDepartmentRepository departmentRepository;

    @MockBean
    EntityManager entityManager;

    @Test
    @DisplayName("Должен найти всех Employee")
    void getAllEmployees() {
        springContextUtils.printAllBeanDefinitions();

        when(employeeRepository.findAllWithPassportAndPhones()).thenReturn(List.of(EmployeeObjectUtils.createEmployeeEntity()));

        List<EmployeeResponseDto> allEmployees = employeeService.getAllEmployees();

        assertEquals(1, allEmployees.size());
    }

    @Test
    @DisplayName("Должен исключить сотрудника из департамента")
    void shouldDeleteDepartmentFromEmployee() {
        EmployeeEntity storedEmployeeEntity = EmployeeObjectUtils.createEmployeeEntity();
        storedEmployeeEntity.setId(UUID.randomUUID());
        UUID employeeId = storedEmployeeEntity.getId();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(storedEmployeeEntity));

        employeeService.removeDepartmentFromEmployee(employeeId.toString(), "АХО");

        assertEquals(0, storedEmployeeEntity.getDepartments().size());
    }

    @Test
    @DisplayName("Должен выбросить исключение если такого департамента нет")
    void should_throwException_when_deleteDepartmentFromEmployee() {
        EmployeeEntity storedEmployeeEntity = EmployeeObjectUtils.createEmployeeEntity();
        storedEmployeeEntity.setId(UUID.randomUUID());
        UUID employeeId = storedEmployeeEntity.getId();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(storedEmployeeEntity));

        assertThrows(EmployeeDepartmentNotFoundException.class,
                () -> employeeService.removeDepartmentFromEmployee(employeeId.toString(), "wrong departmentName"));
        assertEquals(1, storedEmployeeEntity.getDepartments().size());
    }

}