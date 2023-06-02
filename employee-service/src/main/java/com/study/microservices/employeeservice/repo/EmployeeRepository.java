package com.study.microservices.employeeservice.repo;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    Optional<EmployeeEntity> findByEmployeeNameAndEmployeeSurname(String employeeName, String employeeSurname);

    Page<EmployeeEntity> findAllByEmployeeNameOrderByEmployeeBirthDate(String employeeName, Pageable page);
}
