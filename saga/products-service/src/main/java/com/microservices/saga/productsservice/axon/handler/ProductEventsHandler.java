package com.microservices.saga.productsservice.axon.handler;

import com.microservices.saga.productsservice.axon.event.ProductCreatedEvent;
import com.microservices.saga.productsservice.exception.handler.ProductsServiceEventsErrorHandler;
import com.microservices.saga.productsservice.model.entity.ProductEntity;
import com.microservices.saga.productsservice.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        productsRepository.save(productEntity);

//        if (true) throw new RuntimeException("Test rollback exception");
    }

}
