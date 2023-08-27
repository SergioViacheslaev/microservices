package com.study.microservices.employeeservice.service;

import com.study.microservices.employeeservice.integration.config.IntegrationTestConfiguration;
import com.study.microservices.employeeservice.integration.config.SpringContextUtils;
import com.study.microservices.employeeservice.model.dto.EmployeeResponseDto;
import com.study.microservices.employeeservice.model.mapper.EmployeeMapperImpl;
import com.study.microservices.employeeservice.objects_utils.EmployeeTestDataUtils;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        when(employeeRepository.findAllWithPassportAndPhones()).thenReturn(List.of(EmployeeTestDataUtils.createEmployeeEntity()));

        List<EmployeeResponseDto> allEmployees = employeeService.getAllEmployees();

        assertEquals(1, allEmployees.size());
    }

}