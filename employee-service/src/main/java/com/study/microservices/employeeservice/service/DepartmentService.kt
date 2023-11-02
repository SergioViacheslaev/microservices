package com.study.microservices.employeeservice.service

import com.study.microservices.employeeservice.exception.EmployeeDepartmentNotFoundException
import com.study.microservices.employeeservice.model.dto.DepartmentResponseDto
import com.study.microservices.employeeservice.model.dto.DepartmentUpdateRequestDto
import com.study.microservices.employeeservice.repo.EmployeeDepartmentRepository
import lombok.RequiredArgsConstructor
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@RequiredArgsConstructor
class DepartmentService(
    private val employeeDepartmentRepository: EmployeeDepartmentRepository
) {

    @Transactional(readOnly = true)
    fun getAllDepartments(): List<DepartmentResponseDto> {
        return employeeDepartmentRepository.findAll().map { DepartmentResponseDto(it.departmentName) }
    }

    @Transactional(readOnly = true)
    fun getAllDepartmentsFilteredByNames(departmentNames: List<String>): List<DepartmentResponseDto> {
        return employeeDepartmentRepository.findAllByDepartmentNameIn(departmentNames)
            .map { DepartmentResponseDto(it.departmentName) }
    }

    @Transactional
    fun deleteDepartmentByName(departmentName: String) {
        return employeeDepartmentRepository.deleteDepartmentByName(departmentName)
    }

    /**
     * Example of transaction retry for ObjectOptimisticLockingFailureException
     */
    @Transactional
    @Retryable(retryFor = [ObjectOptimisticLockingFailureException::class], maxAttempts = 3, backoff = Backoff(delay = 100))
    fun updateDepartmentById(departmentId: String, departmentUpdateRequestDto: DepartmentUpdateRequestDto): DepartmentResponseDto {
        return employeeDepartmentRepository.findById(UUID.fromString(departmentId))
            .map {
                it.departmentName = departmentUpdateRequestDto.departmentName
                DepartmentResponseDto(it.departmentName)
            }
            .orElseThrow { EmployeeDepartmentNotFoundException("Department with id $departmentId not found") }
    }

}
