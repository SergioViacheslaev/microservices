package com.microservices.saga.productsservice.axon.handler;

import com.microservices.saga.productsservice.axon.event.ProductCreatedEvent;
import com.microservices.saga.productsservice.axon.interceptor.CreateProductCommandInterceptor;
import com.microservices.saga.productsservice.axon.lookup.ProductLookupEntity;
import com.microservices.saga.productsservice.axon.lookup.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Persist ProductLookupEntity in the database
 *
 * @see CreateProductCommandInterceptor
 */
@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    private final ProductLookupRepository productLookupRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        val productLookupEntity = new ProductLookupEntity(event.getProductId(),
                event.getTitle());

        productLookupRepository.save(productLookupEntity);
    }

}
