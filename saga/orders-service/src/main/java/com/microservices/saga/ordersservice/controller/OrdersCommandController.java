package com.microservices.saga.ordersservice.controller;

import com.microservices.saga.ordersservice.axon.command.CreateOrderCommand;
import com.microservices.saga.ordersservice.axon.query.FindOrderQuery;
import com.microservices.saga.ordersservice.model.OrderStatus;
import com.microservices.saga.ordersservice.model.dto.OrderCreateRequestDto;
import com.microservices.saga.ordersservice.model.dto.OrderSummaryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersCommandController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping
    public OrderSummaryDto createOrder(@Valid @RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        log.info("Received createOrder request {}", orderCreateRequestDto);

        val userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
        val orderId = UUID.randomUUID().toString();
        val createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .addressId(orderCreateRequestDto.addressId())
                .productId(orderCreateRequestDto.productId())
                .userId(userId)
                .quantity(orderCreateRequestDto.quantity())
                .orderStatus(OrderStatus.CREATED)
                .build();

        try (SubscriptionQueryResult<OrderSummaryDto, OrderSummaryDto> queryResult = queryGateway.subscriptionQuery(
                new FindOrderQuery(orderId), ResponseTypes.instanceOf(OrderSummaryDto.class),
                ResponseTypes.instanceOf(OrderSummaryDto.class))) {
            commandGateway.sendAndWait(createOrderCommand);
            return queryResult.updates().blockFirst();
        }
    }

}
