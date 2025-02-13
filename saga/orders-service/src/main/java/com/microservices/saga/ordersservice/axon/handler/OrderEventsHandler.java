package com.microservices.saga.ordersservice.axon.handler;

import com.microservices.saga.ordersservice.axon.event.OrderApprovedEvent;
import com.microservices.saga.ordersservice.axon.event.OrderCreatedEvent;
import com.microservices.saga.ordersservice.axon.event.OrderRejectedEvent;
import com.microservices.saga.ordersservice.exception.OrderNotFoundException;
import com.microservices.saga.ordersservice.model.entity.OrderEntity;
import com.microservices.saga.ordersservice.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private final OrdersRepository ordersRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        val orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);

        ordersRepository.save(orderEntity);

        log.info("orderEntity saved to DB: {}", orderEntity);
    }

    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        val orderEntity = ordersRepository.findByOrderId(orderApprovedEvent.getOrderId());

        if (orderEntity == null) {
            log.error("Order with id {} not found", orderApprovedEvent.getOrderId());
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderApprovedEvent.getOrderId()));
        }

        orderEntity.setOrderStatus(orderApprovedEvent.getOrderStatus());
        ordersRepository.save(orderEntity);

        log.info("Updated order {} status to {}", orderEntity.getOrderId(), orderEntity.getOrderStatus());
    }

    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        val orderEntity = ordersRepository.findByOrderId(orderRejectedEvent.getOrderId());
        orderEntity.setOrderStatus(orderRejectedEvent.getOrderStatus());
        ordersRepository.save(orderEntity);
    }

}
