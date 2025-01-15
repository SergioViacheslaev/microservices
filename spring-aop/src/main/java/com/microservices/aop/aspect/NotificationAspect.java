package com.microservices.aop.aspect;

import com.microservices.aop.model.PaymentDto;
import com.microservices.aop.service.NotificationService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotificationAspect {

    private final NotificationService notificationService;

    public NotificationAspect(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @AfterReturning(
            pointcut = "execution(* com.microservices.aop.service.PaymentService.analyzeSuspiciousPayment(*))",
            returning = "payment")
    public void logPaymentFinished(PaymentDto payment) {
        notificationService.notifyFSB(payment);
    }

}
