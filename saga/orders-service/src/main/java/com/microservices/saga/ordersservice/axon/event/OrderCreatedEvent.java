package com.microservices.saga.ordersservice.axon.event;

import com.microservices.saga.ordersservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId; 
    private OrderStatus orderStatus;
}
