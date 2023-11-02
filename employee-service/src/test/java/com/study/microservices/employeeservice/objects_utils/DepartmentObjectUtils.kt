package com.study.microservices.employeeservice.objects_utils

import com.github.javafaker.Faker
import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity


fun getRandomDepartments(count: Int): List<EmployeeDepartmentEntity> {
    val faker = Faker()
    val employeeDepartmentEntities = ArrayList<EmployeeDepartmentEntity>()
    for (i in 0 until count) {
        val departmentEntity = EmployeeDepartmentEntity()
        departmentEntity.departmentName = faker.commerce().department() + faker.random().nextLong()
        employeeDepartmentEntities.add(departmentEntity)
    }
    return employeeDepartmentEntities
}
