package com.study.microservices.employeeservice.repo;

import com.study.microservices.employeeservice.model.entity.EmployeePhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeePhoneRepository extends JpaRepository<EmployeePhoneEntity, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
}
