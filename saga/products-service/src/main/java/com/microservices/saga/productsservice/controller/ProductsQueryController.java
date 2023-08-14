package com.microservices.saga.productsservice.controller;

import com.microservices.saga.productsservice.axon.query.FindProductsQuery;
import com.microservices.saga.productsservice.model.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public List<ProductResponseDto> getProducts() {
        log.info("Received getProducts request");
        val findProductsQuery = new FindProductsQuery();
        return queryGateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductResponseDto.class)).join();
    }

}
