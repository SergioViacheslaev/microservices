package com.study.microservices.salaryprocessingservice.service.kafka;

import com.study.microservices.salaryprocessingservice.api.EmployeeServiceApiClient;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC;
import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC_KEY;
import static com.study.microservices.salaryprocessingservice.utils.DtoUtils.getEmployeePayments;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryProcessingService {

    private final EmployeeServiceApiClient employeeServiceClient;
    private final KafkaTemplate<String, EmployeePaymentDto> kafkaTemplate;

    public void processEmployeesSalary() {
        val employees = employeeServiceClient.getAllEmployees();
        val employeesPayments = getEmployeePayments(employees);

        employeesPayments.forEach(employeePayment -> sendEmployeePayment(SALARY_PAYMENT_TOPIC, SALARY_PAYMENT_TOPIC_KEY, employeePayment));
    }

    private void sendEmployeePayment(String topicName, String key, EmployeePaymentDto employeePayment) {
        val sendResultAsync = kafkaTemplate.send(topicName, key, employeePayment);

        sendResultAsync.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                log.error("Error sending employee payment {} to Kafka topic: {}, key: {} ", employeePayment, topicName, key);
                sendResultAsync.completeExceptionally(exception);
            } else {
                log.info("Sent employee payment {} to Kafka topic: {}, key: {} ", employeePayment, topicName, key);
                sendResultAsync.complete(sendResult);
            }
        });
    }

}
