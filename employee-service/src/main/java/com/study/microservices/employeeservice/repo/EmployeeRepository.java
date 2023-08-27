package com.study.microservices.employeeservice.repo;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.projection.EmployeeView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    Optional<EmployeeView> findEmployeeEntityById(UUID id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"passport", "phones",})
    @Query(value = "SELECT e FROM EmployeeEntity e")
    List<EmployeeEntity> findAllWithPassportAndPhones();

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"departments"})
    @Query(value = "SELECT e FROM EmployeeEntity e")
    List<EmployeeEntity> findAllWithDepartments();

    Optional<EmployeeEntity> findByNameAndSurname(String employeeName, String employeeSurname);

    @Query(
            value = """ 
                    SELECT e,e_passport, e_phone FROM EmployeeEntity e
                    left join fetch EmployeePassportEntity e_passport on e.id = e_passport.employeeId
                    left join fetch EmployeePhoneEntity e_phone on e.id = e_phone.employee.id
                    where e_phone.phoneNumber = :phoneNumber
                    """)
    Optional<EmployeeEntity> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(
            value = """ 
                    SELECT e,e_passport FROM EmployeeEntity e
                    left join fetch EmployeePassportEntity e_passport on e.id = e_passport.employeeId
                    where e_passport.passportNumber = :passportNumber
                    """)
    Optional<EmployeeEntity> findByPassportNumber(@Param("passportNumber") String passportNumber);

    Page<EmployeeEntity> findAllBySurnameOrderByBirthDate(String employeeSurname, Pageable page);
}
