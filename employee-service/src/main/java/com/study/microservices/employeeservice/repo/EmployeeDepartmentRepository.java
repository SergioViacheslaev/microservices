package com.study.microservices.employeeservice.repo;

import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartmentEntity, UUID> {

    Optional<EmployeeDepartmentEntity> findByDepartmentName(String departmentName);
}
