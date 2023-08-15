package com.microservices.saga.productsservice.controller;

import com.microservices.saga.productsservice.axon.command.CreateProductCommand;
import com.microservices.saga.productsservice.model.dto.CreateProductRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsCommandController {

    private final Environment env;
    private final CommandGateway commandGateway;

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductRequestDto createProductRequestDto,
                                @Value("${spring.application.name}") String appName) {
        log.info("Received createProduct request {}", createProductRequestDto);
        val createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(createProductRequestDto.title())
                .price(createProductRequestDto.price())
                .quantity(createProductRequestDto.quantity())
                .build();

        val commandSendResponse = commandGateway.sendAndWait(createProductCommand);

        return "%s on port %s,\nAxon response: %s".formatted(appName, env.getProperty("local.server.port"), commandSendResponse);
    }


}
