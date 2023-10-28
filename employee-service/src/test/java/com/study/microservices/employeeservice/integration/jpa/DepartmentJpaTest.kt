package com.study.microservices.employeeservice.integration.jpa

import com.study.microservices.employeeservice.config.PersistenceConfig
import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity
import com.study.microservices.employeeservice.objects_utils.getRandomDepartments
import com.study.microservices.employeeservice.repo.EmployeeDepartmentRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(PersistenceConfig::class)
class DepartmentJpaTest {

    @Autowired
    lateinit var departmentRepository: EmployeeDepartmentRepository

    @Test
    @DisplayName("Проверка работы batch inserts")
    fun should_save_all_employees_in_batches() {
        val randomList: List<EmployeeDepartmentEntity> = getRandomDepartments(20);

        departmentRepository.saveAll(randomList)

        val storedDepartments = departmentRepository.findAll()

        Assertions.assertEquals(22, storedDepartments.size)
    }
}