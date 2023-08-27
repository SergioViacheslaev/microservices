package com.study.microservices.employeeservice.repo;

import com.study.microservices.employeeservice.model.entity.EmployeeDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartmentEntity, UUID> {

    Optional<EmployeeDepartmentEntity> findByDepartmentName(String departmentName);

    @Modifying
    @Query(value = "delete EmployeeDepartmentEntity where departmentName = :departmentName")
    void deleteDepartmentByName(@Param("departmentName") String departmentName);

}
