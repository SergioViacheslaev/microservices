package com.study.microservices.salaryprocessingservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String SALARY_PAYMENT_TOPIC = "salary-payment-topic";
    public static final String SALARY_PAYMENT_TOPIC_KEY = "salary-payment-key";
    public static final String SALARY_PAYMENT_CONSUMER_GROUP = "salary-payment-group1";

  @Bean
  public NewTopic taskTopic() {
    return TopicBuilder.name("salary-payment-topic")
        .partitions(3)
        .replicas(1)
        .build();
  }
}