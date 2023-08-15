package com.microservices.saga.ordersservice.controller;

import com.microservices.saga.ordersservice.axon.command.CreateOrderCommand;
import com.microservices.saga.ordersservice.model.OrderStatus;
import com.microservices.saga.ordersservice.model.dto.OrderCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
    private final Environment env;


    @PostMapping
    public String createOrder(@Valid @RequestBody OrderCreateRequestDto orderCreateRequestDto,
                              @Value("${spring.application.name}") String appName) {
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

        val commandSendResponse = commandGateway.sendAndWait(createOrderCommand);

        return "%s on port %s,\nAxon response: %s".formatted(appName, env.getProperty("local.server.port"), commandSendResponse);
    }

}
