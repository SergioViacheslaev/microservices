package com.study.microservices.salaryprocessingservice.service.kafka;

import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_CONSUMER_GROUP;
import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC;

@Slf4j
@Component
public class SalaryProcessingListeners {

    @Transactional
    @KafkaListener(topics = SALARY_PAYMENT_TOPIC, groupId = SALARY_PAYMENT_CONSUMER_GROUP)
    public void consume(
            @Payload EmployeePaymentDto employeePayment,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key
//            Acknowledgment ack
    ) {
        log.info("Consumed key: {}", key);
        log.info("Consumed employeePayment: {}", employeePayment);
//        ack.acknowledge();
    }

}