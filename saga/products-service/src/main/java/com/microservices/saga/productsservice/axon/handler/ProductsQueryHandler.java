package com.microservices.saga.productsservice.axon.handler;

import com.microservices.saga.productsservice.axon.query.FindProductsQuery;
import com.microservices.saga.productsservice.model.dto.ProductResponseDto;
import com.microservices.saga.productsservice.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductsQueryHandler {

    private final ProductsRepository productsRepository;

    @QueryHandler
    public List<ProductResponseDto> findProducts(FindProductsQuery query) {
        val storedProductEntities = productsRepository.findAll();

        val foundProductsResponse = new ArrayList<ProductResponseDto>();
        storedProductEntities.forEach(productEntity -> {
            val productResponseDto = new ProductResponseDto();
            BeanUtils.copyProperties(productEntity, productResponseDto);
            foundProductsResponse.add(productResponseDto);
        });

        return foundProductsResponse;
    }

}
