package com.study.microservices.employeeservice.service

import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity
import com.study.microservices.employeeservice.repo.EmployeeDepartmentRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DepartmentServiceTest {

    private val DEPARTMENT_NAME = "Управление автоматизации"

    @Mock
    lateinit var departmentRepository: EmployeeDepartmentRepository

    @InjectMocks
    lateinit var departmentService: DepartmentService

    @Test
    fun getAllDepartments() {
        Mockito.`when`(departmentRepository.findAll()).then {
            listOf(
                EmployeeDepartmentEntity.builder()
                    .departmentName(DEPARTMENT_NAME)
                    .build()
            )
        }

        val allDepartments = departmentService.getAllDepartments()

        assertEquals(1, allDepartments.size)
    }

    @Test
    fun deleteDepartmentByName() {
        Mockito.doNothing().`when`(departmentRepository).deleteDepartmentByName(Mockito.any())

        departmentService.deleteDepartmentByName(DEPARTMENT_NAME)

        Mockito.verify(departmentRepository, times(1)).deleteDepartmentByName(DEPARTMENT_NAME)
    }
}