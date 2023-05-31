package com.study.microservices.employeeservice.dao;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeEntityDao {
    List<EmployeeEntity> findAll();
}
