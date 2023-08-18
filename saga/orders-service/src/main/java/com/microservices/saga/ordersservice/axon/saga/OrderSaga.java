package com.microservices.saga.ordersservice.axon.saga;

import com.microservices.saga.common.command.ProcessPaymentCommand;
import com.microservices.saga.common.command.ReserveProductCommand;
import com.microservices.saga.common.event.PaymentProcessedEvent;
import com.microservices.saga.common.event.ProductReservedEvent;
import com.microservices.saga.ordersservice.axon.command.ApproveOrderCommand;
import com.microservices.saga.ordersservice.axon.event.OrderApprovedEvent;
import com.microservices.saga.ordersservice.axon.event.OrderCreatedEvent;
import com.microservices.saga.ordersservice.util.MockDataUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

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
        //After product is reserved, process user payment
        log.info("Handle ProductReservedEvent {}", productReservedEvent);

        val userPaymentDetails = MockDataUtils.getPaymentDetails();

        val processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetails(userPaymentDetails)
                .paymentId(UUID.randomUUID().toString())
                .build();

        String processPaymentResult;
        try {
            processPaymentResult = commandGateway.sendAndWait(processPaymentCommand);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            // Start compensating transaction
            return;
        }


        if (processPaymentResult == null) {
            log.info("The ProcessPaymentCommand resulted in NULL. Initiating a compensating transaction");
            // Start compensating transaction
        }

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent) {
        //If payment processed successfully, send an ApproveOrderCommand
        val approveOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());

        commandGateway.send(approveOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        log.info("Order is approved. Order Saga is complete for orderId: " + orderApprovedEvent.getOrderId());
        SagaLifecycle.end();
    }


}
