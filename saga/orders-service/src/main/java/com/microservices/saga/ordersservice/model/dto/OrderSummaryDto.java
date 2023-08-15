package com.microservices.saga.ordersservice.model.dto;

import com.microservices.saga.ordersservice.model.OrderStatus;

public record OrderSummaryDto(String orderId, OrderStatus orderStatus, String message) {

}
