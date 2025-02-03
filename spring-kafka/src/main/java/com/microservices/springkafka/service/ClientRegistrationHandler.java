package com.microservices.springkafka.service;

import com.microservices.springkafka.model.dto.ClientRegistrationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Получает список клиентов для регистрации
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientRegistrationHandler {

    private final ClientRegistrationService registrationService;

    @KafkaListener(topics = "${client.registrationTopic}", groupId = "${client.registrationGroup}")
    public void handleClientRegistration(ConsumerRecords<String, ClientRegistrationDto> consumerRecords,
                                         Acknowledgment ack) {
        try {
            final List<ClientRegistrationDto> clientsToRegister = new ArrayList<>();
            consumerRecords.forEach(record -> {
                log.info("Consumed partition: {}", record.partition());
                log.info("Consumed key: {}", record.key());
                log.info("Consumed clientRegistrationDto: {}", record.value());
                log.info("Consumed offset {}", record.offset());
                clientsToRegister.add(record.value());
            });
            log.info("Client registrations batch size: {}", clientsToRegister.size());

            registrationService.registerClients(clientsToRegister);
        } catch (Exception e) {
            log.info("Client registration error ", e);
        } finally {
            ack.acknowledge();
        }
    }

}
