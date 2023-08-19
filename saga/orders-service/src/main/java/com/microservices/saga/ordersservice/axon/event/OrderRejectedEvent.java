package com.microservices.saga.ordersservice.axon.event;

import com.microservices.saga.ordersservice.model.OrderStatus;
import lombok.Value;

@Value
public class OrderRejectedEvent {
    String orderId;
    String reason;
    OrderStatus orderStatus = OrderStatus.REJECTED;
}
