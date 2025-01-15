package com.microservices.aop.service;

import com.microservices.aop.aspect.LogExecutionTime;
import com.microservices.aop.aspect.LogPayment;
import com.microservices.aop.model.PaymentDto;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.microservices.aop.model.PaymentStatus.PROCESSED;
import static com.microservices.aop.model.PaymentStatus.REJECTED;

/**
 * Сервис обработки платежей
 */
@Service
public class PaymentService {

    @LogPayment
    @LogExecutionTime
    public PaymentDto processPayment(PaymentDto payment) {
        simulateLatency();
        System.out.printf("Processing payment %s%n", payment);
        return payment.withStatus(PROCESSED);
    }

    @LogPayment
    public PaymentDto rejectPayment(PaymentDto payment) {
        System.out.printf("Rejecting payment %s%n", payment);
        return payment.withStatus(REJECTED);
    }

    public PaymentDto analyzeSuspiciousPayment(PaymentDto payment) {
        System.out.printf("Analyzing suspicious payment %s%n", payment);
        return payment;
    }

    /**
     * Имитирует задержку выполнения
     */
    private void simulateLatency() {
        try {
            Thread.sleep(new Random().nextLong(1_000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
