package com.study.microservices.employeeservice.repo;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import com.study.microservices.employeeservice.model.projection.EmployeeView;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID>, JpaSpecificationExecutor<EmployeeEntity> {

    @Query("""
            select e
            from EmployeeEntity e
            """
    )
    @QueryHints(
            @QueryHint(name = AvailableHints.HINT_FETCH_SIZE, value = "25")
    )
    Stream<EmployeeEntity> findAllEmployees();

    @Override
    @EntityGraph(
            //EntityGraph.EntityGraphType.FETCH only loads child entities from attributePaths
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"phones"})
    Optional<EmployeeEntity> findById(UUID uuid);

    Optional<EmployeeView> findEmployeeEntityById(UUID id);

    Optional<EmployeeEntity> findByNameAndSurname(String employeeName, String employeeSurname);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"passport", "phones",})
    @Query(value = "SELECT e FROM EmployeeEntity e")
    List<EmployeeEntity> findAllWithPassportAndPhones();

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"passport", "phones"})
    Optional<EmployeeEntity> findByPhones_PhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(
            value = """ 
                    SELECT e,e_passport FROM EmployeeEntity e
                    left join fetch EmployeePassportEntity e_passport on e.id = e_passport.employeeId
                    where e_passport.passportNumber = :passportNumber
                    """)
    Optional<EmployeeEntity> findByPassportNumber(@Param("passportNumber") String passportNumber);

    Page<EmployeeEntity> findAllBySurnameOrderByBirthDate(String employeeSurname, Pageable page);

    boolean existsEmployeeEntityByNameAndSurname(String employeeName, String employeeSurname);
}
