package com.study.microservices.employeeservice.integration.jpa;

import com.study.microservices.employeeservice.config.PersistenceConfig;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static com.study.microservices.employeeservice.objects.EmployeeTestDataUtils.createEmployeeEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PersistenceConfig.class)
//@AutoConfigureTestDatabase override to use other db than h2
public class EmployeeServiceJpaTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Должен сохранять Employee")
    void should_save_employee() {
        val employeeEntityToSave = createEmployeeEntity();
        employeeRepository.save(employeeEntityToSave);

        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findByEmployeeNameAndEmployeeSurname("Alex", "Ivanov");

        assertTrue(optionalEmployee.isPresent());
        val employeeEntity = optionalEmployee.get();
        assertThat(employeeEntity.getEmployeePhones().size()).isEqualTo(1);
        assertEquals(employeeEntityToSave.getEmployeeName(), employeeEntity.getEmployeeName());
        assertEquals(employeeEntityToSave.getEmployeeSurname(), employeeEntity.getEmployeeSurname());

        assertNotNull(employeeEntity.getEmployeeId());
        assertNotNull(employeeEntity.getCreatedOn());
        assertNotNull(employeeEntity.getUpdatedOn());
        assertNull(employeeEntity.getCreatedBy());
        assertNull(employeeEntity.getUpdatedBy());
    }

}
