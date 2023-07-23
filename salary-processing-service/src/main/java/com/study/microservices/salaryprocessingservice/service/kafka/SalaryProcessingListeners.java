package com.study.microservices.salaryprocessingservice.service.kafka;

import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_CONSUMER_GROUP;
import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC;

import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalaryProcessingListeners {

    @KafkaListener(topics = SALARY_PAYMENT_TOPIC, groupId = SALARY_PAYMENT_CONSUMER_GROUP)
    public void consume(
            @Payload EmployeePaymentDto employeePayment,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        log.info("Consumed key: {}", key);
        log.info("Consumed employeePayment: {}", employeePayment);
    }

}