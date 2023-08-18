package com.microservices.saga.ordersservice.axon.event;


import com.microservices.saga.ordersservice.model.OrderStatus;
import lombok.Value;

@Value
public class OrderApprovedEvent {
    String orderId;
    OrderStatus orderStatus = OrderStatus.APPROVED;
}

