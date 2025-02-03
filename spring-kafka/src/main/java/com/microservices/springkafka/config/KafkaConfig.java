package com.microservices.springkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${client.registrationTopic}")
    private String clientRegistrationTopic;

    @Value("${client.registeredTopic}")
    private String clientRegisteredTopic;

    /**
     * Топик для регистрации новых клиентов
     */
    @Bean
    public NewTopic clientRegistrationTopic() {
        return TopicBuilder.name(clientRegistrationTopic)
                           .partitions(3)
                           .replicas(1)
                           .build();
    }

    /**
     * Топик для уже зарегистрированных (сохраненных в БД) клиентов
     */
    @Bean
    public NewTopic clientRegisteredTopic() {
        return TopicBuilder.name(clientRegisteredTopic)
                           .partitions(3)
                           .replicas(1)
                           .build();
    }
}
