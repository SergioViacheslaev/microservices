package com.microservices.aop;

import com.microservices.aop.config.AppConfig;
import com.microservices.aop.model.PaymentDto;
import com.microservices.aop.model.PaymentStatus;
import com.microservices.aop.service.PaymentService;
import com.microservices.aop.service.ValidationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var paymentService = context.getBean("paymentService", PaymentService.class);

        var paymentDto = new PaymentDto(UUID.randomUUID(), "42423442342", "12223344",
                                        BigDecimal.valueOf(10_000), "Оплата ЖКХ", PaymentStatus.NEW);
        var processedPayment = paymentService.processPayment(paymentDto);
        System.out.println("------------------");

        var rejectedPayment = paymentService.rejectPayment(paymentDto);
        System.out.println("------------------");

        var suspiciousPayment = paymentService.analyzeSuspiciousPayment(paymentDto);
        System.out.println("------------------");

        var validationService = context.getBean("validationService", ValidationService.class);
        try {
            validationService.validatePayment(null);
        } catch (Exception e) {
            //LoggingAspect will log exception
        }

        context.close();
    }
}
