package com.study.microservices.salaryprocessingservice.service.kafka;

import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC;
import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC_KEY;
import static com.study.microservices.salaryprocessingservice.utils.DtoUtils.getEmployeePayments;
import static java.time.Duration.ofSeconds;

import com.study.microservices.salaryprocessingservice.api.EmployeeServiceApiClient;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import java.util.Random;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SalaryProcessingService {

    private final EmployeeServiceApiClient employeeServiceClient;
    private final KafkaTemplate<String, EmployeePaymentDto> kafkaTemplate;

    public SalaryProcessingService(
            @Qualifier("com.study.microservices.salaryprocessingservice.api.EmployeeServiceApiClient")
            EmployeeServiceApiClient employeeServiceClient,
            KafkaTemplate<String, EmployeePaymentDto> kafkaTemplate
    ) {
        this.employeeServiceClient = employeeServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("kafkaTransactionManager")
    public void processEmployeesSalary() {
        val employees = employeeServiceClient.getAllEmployees();
        val employeesPayments = getEmployeePayments(employees);

        employeesPayments.forEach(employeePayment -> sendEmployeePayment(SALARY_PAYMENT_TOPIC, SALARY_PAYMENT_TOPIC_KEY, employeePayment));
    }

    @SneakyThrows
    private void sendEmployeePayment(String topicName, String key, EmployeePaymentDto employeePayment) {
        log.info("Sending thread {}", Thread.currentThread());
        log.info("Sending employeePayment {}", employeePayment);
        val sendResultAsync = kafkaTemplate.send(topicName, key, employeePayment);

        sendResultAsync.whenComplete((sendResult, exception) -> {
            final var partition = sendResult.getRecordMetadata().partition();
            if (exception != null) {
                log.error("Error producer sending employee payment {} to Kafka topic: {},  partition: {}, key: {} ", employeePayment, topicName,
                        partition, key);
                sendResultAsync.completeExceptionally(exception);
            } else {
                log.info("Producer sent employee payment {} to Kafka topic: {}, partition: {}, key: {} ", employeePayment, topicName,
                        partition, key);
                sendResultAsync.complete(sendResult);
            }
        });

//      fake latency between each sending
        Thread.sleep(ofSeconds(5).toMillis());

//        simulateRandomException();
    }

    private void simulateRandomException() {
        var randomInt = new Random().ints(1, 4)
                .findFirst()
                .orElse(3);
        log.info("randomInt is {}", randomInt);
        if (randomInt == 3) {
            throw new RuntimeException("randomInt = " + randomInt);
        }
    }

}
