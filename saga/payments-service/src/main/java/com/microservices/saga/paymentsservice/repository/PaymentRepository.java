package com.microservices.saga.paymentsservice.repository;

import com.microservices.saga.paymentsservice.model.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

}