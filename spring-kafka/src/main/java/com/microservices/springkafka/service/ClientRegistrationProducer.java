package com.microservices.springkafka.service;

import com.microservices.springkafka.model.dto.ClientRegistrationDto;
import com.microservices.springkafka.util.MockDataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Отправляет мок-список клиентов для регистрации в ClientRegistrationHandler
 * Логирует успех или ошибку отправки
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRegistrationProducer {

    @Value("${client.registrationTopic}")
    private String clientsRegistrationTopic;

    private final KafkaTemplate<String, ClientRegistrationDto> kafkaTemplate;
    private final MockDataUtils mockDataUtils;

    public void sendClientsRegistrationData() {
        mockDataUtils.getMockClients().forEach(clientRegistrationDto -> {
            var sendResultAsync = kafkaTemplate.send(clientsRegistrationTopic, clientRegistrationDto);
            sendResultAsync.whenComplete((sendResult, exception) -> {
                var partition = sendResult.getRecordMetadata().partition();
                var topic = sendResult.getRecordMetadata().topic();

                if (exception != null) {
                    log.error("Error sending clients registration Dto {} to Kafka topic: {}, partition: {}", clientRegistrationDto, topic, partition, exception);
                    sendResultAsync.completeExceptionally(exception);
                } else {
                    log.info("Sent clients registration Dto {} to Kafka topic: {}, partition: {}", clientRegistrationDto, topic, partition);
                    sendResultAsync.complete(sendResult);
                }
            });
        });
    }

}
