package com.study.microservices.employeeservice.repo;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    Optional<EmployeeEntity> findByEmployeeNameAndEmployeeSurname(String employeeName, String employeeSurname);

    @Query(
            value = "SELECT * FROM employee left join employee_phone using (employee_id) where phone_number = :phoneNumber",
            nativeQuery = true)
    Optional<EmployeeEntity> findByEmployeePhoneNumber(@Param("phoneNumber") String phoneNumber);

    Page<EmployeeEntity> findAllByEmployeeNameOrderByEmployeeBirthDate(String employeeName, Pageable page);
}
