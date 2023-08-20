package com.microservices.saga.ordersservice.model.dto;

import com.microservices.saga.ordersservice.model.OrderStatus;
import lombok.Value;

@Value
public class OrderSummaryDto {
    String orderId;
    OrderStatus orderStatus;
    String message;
}
