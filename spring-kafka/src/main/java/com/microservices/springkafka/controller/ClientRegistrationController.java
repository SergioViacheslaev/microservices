package com.microservices.springkafka.controller;

import com.microservices.springkafka.service.ClientRegistrationProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/client")
@RequiredArgsConstructor
public class ClientRegistrationController {

    private final ClientRegistrationProducer registrationProducer;

    /**
     * Инициализует процесс регистрации клиентов из файла resources/MOCK_DATA.json
     */
    @PostMapping(value = "/register")
    public void registerMockClients() {
        log.info("Received clients registration request");
        registrationProducer.sendClientsRegistrationData();
    }

}
