package com.study.microservices.employeeservice.controller

import com.study.microservices.employeeservice.model.dto.DepartmentResponseDto
import com.study.microservices.employeeservice.service.DepartmentService
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.PathVariable


@RestController
@RequestMapping("/api/v1/departments")
class DepartmentController(
    val departmentService: DepartmentService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllDepartments(): List<DepartmentResponseDto> {
        log.info("Received getAllDepartments request")
        return departmentService.getAllDepartments()
    }

    @DeleteMapping("/{departmentName}")
    @ResponseStatus(NO_CONTENT)
    fun deleteDepartmentByName(
        @Parameter(schema = Schema(defaultValue = "Управление автоматизации"))
        @PathVariable departmentName: String
    ) {
        log.info("Received deleteDepartmentByName $departmentName request")
        departmentService.deleteDepartmentByName(departmentName)
    }
}
