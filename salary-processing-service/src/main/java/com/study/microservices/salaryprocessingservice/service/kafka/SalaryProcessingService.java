package com.study.microservices.salaryprocessingservice.service.kafka;

import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC;
import static com.study.microservices.salaryprocessingservice.config.KafkaConfig.SALARY_PAYMENT_TOPIC_KEY;
import static com.study.microservices.salaryprocessingservice.utils.DtoUtils.getEmployeePaymentDummy;
import static com.study.microservices.salaryprocessingservice.utils.DtoUtils.getEmployeePayments;

import com.study.microservices.salaryprocessingservice.api.EmployeeServiceApiClient;
import com.study.microservices.salaryprocessingservice.model.dto.EmployeePaymentDto;
import com.study.microservices.salaryprocessingservice.utils.DtoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryProcessingService {

    private final EmployeeServiceApiClient employeeServiceClient;
    private final KafkaTemplate<String, EmployeePaymentDto> kafkaTemplate;

    public void processEmployeesSalary() {
//        Call external service
//        val employees = employeeServiceClient.getAllEmployees();
//        val employeesPayments = getEmployeePayments(employees);

        sendEmployeePayment(SALARY_PAYMENT_TOPIC, SALARY_PAYMENT_TOPIC_KEY, getEmployeePaymentDummy());
    }

    private void sendEmployeePayment(String topicName, String key, EmployeePaymentDto employeePayment) {
        val sendResultAsync = kafkaTemplate.send(topicName, key, employeePayment);

        sendResultAsync.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                sendResultAsync.completeExceptionally(exception);
            } else {
                sendResultAsync.complete(sendResult);
            }
            log.info("SalaryProcessingService sent employee payment {} to Kafka topic: {}, key: {} ", employeePayment,
                    topicName, key);
        });
    }

}
