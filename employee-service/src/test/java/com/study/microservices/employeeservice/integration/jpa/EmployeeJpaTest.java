package com.study.microservices.employeeservice.integration.jpa;

import com.study.microservices.employeeservice.config.PersistenceConfig;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.repo.EmployeeRepository;
import jakarta.persistence.EntityManager;
import lombok.val;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.study.microservices.employeeservice.objects_utils.EmployeeObjectUtils.createEmployeeEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(PersistenceConfig.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) override to use other db than h2
class EmployeeJpaTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    EmployeeRepository employeeRepository;

    Statistics statistics;

    @BeforeEach
    void init() {
        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        statistics = sessionFactory.getStatistics();
    }

    @AfterEach
    void tearDown() {
        statistics.clear();
    }

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
    @DisplayName("Должен найти всех Employee с пасспортами и телефонами за 1 запрос")
    void should_find_all_employee() {
        List<EmployeeEntity> allEmployees = employeeRepository.findAllWithPassportAndPhones();

        assertThat(allEmployees).isNotNull().hasSize(5);
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Должен найти всех Employee с пасспортами, проблема n+1")
    void should_find_all_employee_with_N_additional_queries_problem() {
        List<EmployeeEntity> allEmployees = employeeRepository.findAll();

        assertThat(allEmployees).isNotNull().hasSize(5);
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(6);
    }

    @Test
    @DisplayName("Должен найти информацию о сотруднике по id за 1 sql запрос")
    void should_find_employee_by_id_with_one_sql_query() {
        val employeeEntity = employeeRepository.findById(UUID.fromString("b16355a9-3edf-418d-bafc-52e46f6703e1")).orElseThrow();

        assertEquals("Ivan", employeeEntity.getName());
        assertEquals("Ivanov", employeeEntity.getSurname());
        assertNotNull(employeeEntity.getPhones());
        assertNotNull(employeeEntity.getPassport());
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

}
