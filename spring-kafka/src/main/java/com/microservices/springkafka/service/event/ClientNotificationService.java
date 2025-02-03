package com.microservices.springkafka.service.event;

import com.microservices.springkafka.model.dto.ClientRegisteredDto;
import com.microservices.springkafka.model.event.ClientsRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Получает ClientsRegisteredEvent в котором содержится список сохраненных в БД клиентов
 * Отправляет уведомление в топик для Зарегистрированных клиентов
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientNotificationService {

    @Value("${client.registeredTopic}")
    private String clientRegisteredTopic;

    private final KafkaTemplate<String, ClientRegisteredDto> kafkaTemplate;

    /**
     * Отправка в топик произойдет только в случае успеха сохранения ClientEntity в транзакции
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processClientSavedEvent(ClientsRegisteredEvent event) {
        event.registeredClients().forEach(client -> {
            var sendResultAsync = kafkaTemplate.send(clientRegisteredTopic, client);
            sendResultAsync.whenComplete((sendResult, exception) -> {
                var partition = sendResult.getRecordMetadata().partition();
                var topic = sendResult.getRecordMetadata().topic();

                if (exception != null) {
                    log.error("Error sending registered client Dto {} to Kafka topic: {}, partition: {}", client, topic, partition, exception);
                    sendResultAsync.completeExceptionally(exception);
                } else {
                    log.info("Sent registered client Dto {} to Kafka topic: {}, partition: {}", client, topic, partition);
                    sendResultAsync.complete(sendResult);
                }
            });
        });
    }
}
