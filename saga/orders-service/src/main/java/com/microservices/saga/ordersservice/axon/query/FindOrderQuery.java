package com.microservices.saga.ordersservice.axon.query;

import lombok.Value;

@Value
public class FindOrderQuery {
    String orderId;
}
