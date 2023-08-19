package com.microservices.saga.ordersservice.axon.saga;

import com.microservices.saga.common.command.CancelProductReservationCommand;
import com.microservices.saga.common.command.ProcessPaymentCommand;
import com.microservices.saga.common.command.ReserveProductCommand;
import com.microservices.saga.common.event.PaymentProcessedEvent;
import com.microservices.saga.common.event.ProductReservationCancelledEvent;
import com.microservices.saga.common.event.ProductReservedEvent;
import com.microservices.saga.ordersservice.axon.command.ApproveOrderCommand;
import com.microservices.saga.ordersservice.axon.command.RejectOrderCommand;
import com.microservices.saga.ordersservice.axon.event.OrderApprovedEvent;
import com.microservices.saga.ordersservice.axon.event.OrderCreatedEvent;
import com.microservices.saga.ordersservice.axon.event.OrderRejectedEvent;
import com.microservices.saga.ordersservice.util.MockDataUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
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
                val reserveProductCommandExceptionMessage = commandResultMessage.exceptionResult().getMessage();
                log.warn("reserveProductCommand failed with reason {}, starting compensating transaction", reserveProductCommandExceptionMessage);
                val rejectOrderCommand = new RejectOrderCommand(orderCreatedEvent.getOrderId(), reserveProductCommandExceptionMessage);
                commandGateway.send(rejectOrderCommand);
            }
        });

        log.info("OrderCreatedEvent handled for orderId {}, productId {} ", reserveProductCommand.getOrderId(), reserveProductCommand.getProductId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        //After product is reserved, process user payment
        log.info("Handle ProductReservedEvent {}", productReservedEvent);

        try {
            val userPaymentDetails = MockDataUtils.createPaymentDetails();

            val processPaymentCommand = ProcessPaymentCommand.builder()
                    .orderId(productReservedEvent.getOrderId())
                    .paymentDetails(userPaymentDetails)
                    .paymentId(UUID.randomUUID().toString())
                    .build();

            val processPaymentResult = commandGateway.sendAndWait(processPaymentCommand);

            if (processPaymentResult == null) {
                log.info("The ProcessPaymentCommand resulted in NULL. Initiating a compensating transaction");
                // Start compensating transaction
                cancelProductReservation(productReservedEvent, "Could not process user payment with provided payment details");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            // Start compensating transaction
            cancelProductReservation(productReservedEvent, ex.getMessage());
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
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent productReservationCancelledEvent) {
        val rejectOrderCommand = new RejectOrderCommand(productReservationCancelledEvent.getOrderId(), productReservationCancelledEvent.getReason());

        commandGateway.send(rejectOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRejectedEvent orderRejectedEvent) {
        log.info("Rejected order with id {}, reason {}", orderRejectedEvent.getOrderId(), orderRejectedEvent.getReason());
    }

    private void cancelProductReservation(ProductReservedEvent productReservedEvent, String reason) {
        val cancelProductReservationCommand = CancelProductReservationCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .productId(productReservedEvent.getProductId())
                .quantity(productReservedEvent.getQuantity())
                .userId(productReservedEvent.getUserId())
                .reason(reason)
                .build();

        commandGateway.send(cancelProductReservationCommand);

        log.info("Sent cancelProductReservationCommand {}", cancelProductReservationCommand);
    }

}
