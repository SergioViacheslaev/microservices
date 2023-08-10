package com.study.microservices.salaryprocessingservice.service.kafka;

import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_CONSUMER_GROUP;
import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC;

import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import com.study.microservices.salaryprocessingservice.repository.EmployeePaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SalaryProcessingListeners {

    private final EmployeePaymentRepository employeePaymentRepository;

    @Transactional("kafkaTransactionManager")
    @KafkaListener(topics = SALARY_PAYMENT_TOPIC, groupId = SALARY_PAYMENT_CONSUMER_GROUP)
    public void consume(
            @Payload EmployeePaymentDto employeePayment,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
            ConsumerRecord<?, ?> consumerRecord,
            Acknowledgment ack
    ) {
        log.info("Consumed partition: {}", consumerRecord.partition());
        log.info("Consumed key: {}", key);
        log.info("Consumed value: {}", employeePayment);
        final var savedPaymentEntity = employeePaymentRepository.save(employeePayment.toEntity());
        log.info("Saved employeePayment id: {}", savedPaymentEntity.getPaymentId());
        ack.acknowledge();
        log.info("Committed offset {}", consumerRecord.offset());
    }

}