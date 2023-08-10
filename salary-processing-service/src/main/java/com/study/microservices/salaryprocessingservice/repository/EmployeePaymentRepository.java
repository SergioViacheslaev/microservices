package com.study.microservices.salaryprocessingservice.repository;

import com.study.microservices.salaryprocessingservice.model.entity.EmployeePaymentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePaymentRepository extends JpaRepository<EmployeePaymentEntity, UUID> {

}
