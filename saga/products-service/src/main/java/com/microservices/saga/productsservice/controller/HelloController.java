package com.microservices.saga.productsservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class HelloController {

    @GetMapping
    public String sayHello(@Value("${spring.application.name}") String appName) {
        return "Hello World from %s".formatted(appName);
    }
}
