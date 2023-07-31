package com.study.microservices.employeeservice.service

import com.study.microservices.employeeservice.model.dto.DepartmentResponseDto
import com.study.microservices.employeeservice.repo.EmployeeDepartmentRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class DepartmentService(
    private val employeeDepartmentRepository: EmployeeDepartmentRepository
) {

    @Transactional(readOnly = true)
    fun getAllDepartments(): List<DepartmentResponseDto> {
        return employeeDepartmentRepository.findAll().map { DepartmentResponseDto(it.departmentName) }
    }
}
