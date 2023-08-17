package com.microservices.saga.ordersservice.axon.saga;

import com.microservices.saga.common.command.ReserveProductCommand;
import com.microservices.saga.common.event.ProductReservedEvent;
import com.microservices.saga.ordersservice.axon.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        val reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();

        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            log.info("Sent reserveProductCommand, messageId {}, result {}", commandMessage.getIdentifier(), commandResultMessage);
            if (commandResultMessage.isExceptional()) {
                log.warn("Starting compensating transaction");
                //todo: rollback changes
            }
        });

        log.info("OrderCreatedEvent handled for orderId {}, productId {} ", reserveProductCommand.getOrderId(), reserveProductCommand.getProductId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        // Process user payment
        log.info("Handle ProductReservedEvent {}", productReservedEvent);

    }


}
