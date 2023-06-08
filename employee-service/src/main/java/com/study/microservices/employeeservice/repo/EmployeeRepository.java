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

    Optional<EmployeeEntity> findByNameAndSurname(String employeeName, String employeeSurname);

    @Query(
            value = """ 
                    SELECT e,e_passport, e_phone FROM EmployeeEntity e
                    left join fetch EmployeePassportEntity e_passport on e.Id = e_passport.employeeId
                    left join fetch EmployeePhoneEntity e_phone on e.Id = e_phone.employee.Id
                    where e_phone.phoneNumber = :phoneNumber
                    """)
    Optional<EmployeeEntity> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    Page<EmployeeEntity> findAllByNameOrderByBirthDate(String employeeName, Pageable page);
}
