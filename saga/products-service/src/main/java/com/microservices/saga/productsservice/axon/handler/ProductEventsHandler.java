package com.microservices.saga.productsservice.axon.handler;

import com.microservices.saga.common.event.ProductReservedEvent;
import com.microservices.saga.productsservice.axon.event.ProductCreatedEvent;
import com.microservices.saga.productsservice.exception.handler.ProductsServiceEventsErrorHandler;
import com.microservices.saga.productsservice.model.entity.ProductEntity;
import com.microservices.saga.productsservice.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private final ProductsRepository productsRepository;

    /**
     * Rethrows exception to ProductsServiceEventsErrorHandler
     *
     * @see ProductsServiceEventsErrorHandler
     */
    @ExceptionHandler()
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        val productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        productsRepository.save(productEntity);

        log.info("productEntity saved to DB: {}", productEntity);

//        if (true) throw new RuntimeException("Test rollback exception");
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        val productEntity = productsRepository.findByProductId(productReservedEvent.getProductId());
        log.info("ProductReservedEvent: Current product quantity " + productEntity.getQuantity());

        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        productsRepository.save(productEntity);

        log.info("ProductReservedEvent: New product quantity " + productEntity.getQuantity());
        log.info("ProductReservedEvent is called for productId:" + productReservedEvent.getProductId() +
                " and orderId: " + productReservedEvent.getOrderId());
    }


}
