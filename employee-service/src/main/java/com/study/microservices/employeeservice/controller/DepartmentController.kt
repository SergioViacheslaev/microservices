package com.study.microservices.employeeservice.controller

import com.study.microservices.employeeservice.model.dto.DepartmentCreateRequestDto
import com.study.microservices.employeeservice.model.dto.DepartmentResponseDto
import com.study.microservices.employeeservice.model.dto.DepartmentUpdateRequestDto
import com.study.microservices.employeeservice.service.DepartmentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/departments")
class DepartmentController(
    val departmentService: DepartmentService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getAllDepartments(
        @RequestParam(value = "name", required = false) departmentNames: List<String>?
    ): List<DepartmentResponseDto> {
        if (departmentNames != null) {
            log.info("Received getAllDepartments filtered by names $departmentNames request")
            return departmentService.getAllDepartmentsFilteredByNames(departmentNames)
        }
        log.info("Received getAllDepartments request")
        return departmentService.getAllDepartments()
    }

    @GetMapping("/names/{departmentNames}")
    fun getAllDepartmentsFilterByNames(@PathVariable departmentNames: List<String>): List<DepartmentResponseDto> {
        log.info("Received getAllDepartments filtered by names {} request", departmentNames)
        return departmentService.getAllDepartmentsFilteredByNames(departmentNames)
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    fun createDepartment(@RequestBody departmentCreateRequestDto: DepartmentCreateRequestDto) {
        log.info("Received createDepartment requestDto $departmentCreateRequestDto")
        //todo: impl....
    }

    @Operation(summary = "Update department by id")
    @PutMapping("/{departmentId}")
    @ResponseStatus(OK)
    fun updateDepartmentById(
        @Parameter(schema = Schema(defaultValue = "0ff29382-4537-42b8-bdac-81a229ba0bd1"))
        @PathVariable departmentId: String,
        @RequestBody departmentUpdateRequestDto: DepartmentUpdateRequestDto
    ): DepartmentResponseDto {
        log.info("Received updateDepartment requestDto $departmentUpdateRequestDto")
        return departmentService.updateDepartmentById(departmentId, departmentUpdateRequestDto)
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
