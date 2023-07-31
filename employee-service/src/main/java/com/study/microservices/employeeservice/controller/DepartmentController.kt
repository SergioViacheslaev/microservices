package com.study.microservices.employeeservice.controller

import com.study.microservices.employeeservice.model.dto.DepartmentResponseDto
import com.study.microservices.employeeservice.service.DepartmentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/departments")
class DepartmentController(
    val departmentService: DepartmentService
) {

    @GetMapping
    fun getAllDepartments(): List<DepartmentResponseDto> {
        return departmentService.getAllDepartments()
    }
}
