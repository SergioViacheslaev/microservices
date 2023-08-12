package com.microservices.saga.productsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
@RequiredArgsConstructor
public class HelloController {

    private final Environment env;

    @GetMapping
    public String sayHello(@Value("${spring.application.name}") String appName) {
        return "Hello World from %s, port %s".formatted(appName, env.getProperty("local.server.port"));
    }
}
