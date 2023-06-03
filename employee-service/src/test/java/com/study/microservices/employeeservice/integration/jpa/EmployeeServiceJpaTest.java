package com.study.microservices.employeeservice.integration.jpa;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@AutoConfigureTestDatabase override to use other db than h2
public class EmployeeServiceJpaTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void should_save_employee() {
        employeeRepository.save(EmployeeEntity.builder().employeeName("Foo").employeeSurname("Bar").employeeBirthDate(LocalDate.now()).build());

        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findByEmployeeNameAndEmployeeSurname("Foo", "Bar");

        assertTrue(optionalEmployee.isPresent());
        val employeeEntity = optionalEmployee.get();
        assertNotNull(employeeEntity.getCreatedOn());
        assertNotNull(employeeEntity.getUpdatedOn());

        assertNull(employeeEntity.getCreatedBy());
        assertNull(employeeEntity.getUpdatedBy());
    }
}
