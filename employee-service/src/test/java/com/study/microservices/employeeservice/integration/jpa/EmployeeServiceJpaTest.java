package com.study.microservices.employeeservice.integration.jpa;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
//@AutoConfigureTestDatabase override to use other db than h2
public class EmployeeServiceJpaTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void should_save_employee() {
        employeeRepository.save(EmployeeEntity.builder()
                .employeeName("Foo")
                .employeeSurname("Bar")
                .employeeBirthDate(LocalDate.now())
                .build());

        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findByEmployeeNameAndEmployeeSurname(
                "Foo",
                "Bar");

        assertTrue(optionalEmployee.isPresent());
    }
}
