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

import java.util.List;
import java.util.Optional;

import static com.study.microservices.employeeservice.objects_utils.EmployeeObjectUtils.createEmployeeEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PersistenceConfig.class)
//@AutoConfigureTestDatabase override to use other db than h2
public class EmployeeJpaTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Должен сохранять Employee")
    void should_save_employee() {
        val employeeEntityToSave = createEmployeeEntity();
        employeeRepository.save(employeeEntityToSave);

        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findByNameAndSurname("Alex", "Ivanov");
        assertTrue(optionalEmployee.isPresent());

        val savedEmployeeEntity = optionalEmployee.get();
        assertThat(savedEmployeeEntity.getPhones().size()).isEqualTo(1);
        assertThat(savedEmployeeEntity.getDepartments().size()).isEqualTo(1);
        assertEquals(employeeEntityToSave.getPassport().getPassportNumber(), savedEmployeeEntity.getPassport().getPassportNumber());
        assertEquals(employeeEntityToSave.getName(), savedEmployeeEntity.getName());
        assertEquals(employeeEntityToSave.getSurname(), savedEmployeeEntity.getSurname());
        assertEquals(employeeEntityToSave.getMonthlySalary(), savedEmployeeEntity.getMonthlySalary());
        assertEquals(employeeEntityToSave.getPayrollAccount(), savedEmployeeEntity.getPayrollAccount());

        assertNotNull(savedEmployeeEntity.getId());
        assertNotNull(savedEmployeeEntity.getCreatedOn());
        assertNotNull(savedEmployeeEntity.getUpdatedOn());
        assertNull(savedEmployeeEntity.getCreatedBy());
        assertNull(savedEmployeeEntity.getUpdatedBy());
    }

    @Test
    @DisplayName("Должен найти Employee по имени и фамилии")
    void should_find_employee() {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findByNameAndSurname("Ivan", "Ivanov");

        assertTrue(optionalEmployee.isPresent());
    }

    @Test
    @DisplayName("Должен найти всех Employee")
    void should_find_all_employee() {
        List<EmployeeEntity> allEmployees = employeeRepository.findAll();

        assertThat(allEmployees).isNotNull().hasSize(5);
    }

}
