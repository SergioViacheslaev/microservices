package com.study.microservices.employeeservice.objects_utils

import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity
import java.util.*


fun getRandomDepartments(count: Int): List<EmployeeDepartmentEntity> {
    val employeeDepartmentEntities = ArrayList<EmployeeDepartmentEntity>()
    for (i in 0 until count) {
        val departmentEntity = EmployeeDepartmentEntity()
        departmentEntity.departmentName = "name " + Random().nextInt()
        employeeDepartmentEntities.add(departmentEntity)
    }
    return employeeDepartmentEntities
}
