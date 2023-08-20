package com.microservices.saga.ordersservice.axon.handler;

import com.microservices.saga.ordersservice.axon.query.FindOrderQuery;
import com.microservices.saga.ordersservice.model.dto.OrderSummaryDto;
import com.microservices.saga.ordersservice.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQueriesHandler {

    private final OrdersRepository ordersRepository;

    @QueryHandler
    public OrderSummaryDto findOrder(FindOrderQuery findOrderQuery) {
        val orderEntity = ordersRepository.findByOrderId(findOrderQuery.getOrderId());
        return new OrderSummaryDto(orderEntity.getOrderId(),
                orderEntity.getOrderStatus(), "");
    }

}
