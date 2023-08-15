package com.microservices.saga.ordersservice.repository;

import com.microservices.saga.ordersservice.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrderEntity, String> {

    OrderEntity findByOrderId(String orderId);
}
