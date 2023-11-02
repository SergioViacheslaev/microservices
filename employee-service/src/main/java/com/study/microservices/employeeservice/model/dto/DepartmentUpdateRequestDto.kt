package com.study.microservices.employeeservice.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DepartmentUpdateRequestDto(
    @JsonProperty("departmentName")
    val departmentName: String
)
