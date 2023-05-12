package com.study.microservices.employeeservice.integration.jpa;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
//@AutoConfigureTestDatabase override to not use H2
public class EmployeeServiceJpaTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void initial_db_data_test() {
        employeeRepository.save(EmployeeEntity.builder()
                .employeeId(UUID.randomUUID())
                .employeeName("test123")
                .build());

        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findByEmployeeName("test123");

        assertTrue(optionalEmployee.isPresent());
    }
}
