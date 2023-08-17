package com.microservices.saga.ordersservice.axon.handler;

import com.microservices.saga.ordersservice.axon.event.OrderCreatedEvent;
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


}
