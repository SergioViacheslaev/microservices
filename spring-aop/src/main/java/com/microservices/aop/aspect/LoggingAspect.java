package com.microservices.aop.aspect;

import com.microservices.aop.exception.PaymentValidationException;
import com.microservices.aop.model.PaymentDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(LogPayment)")
    public void logPaymentStarted(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        var payment = (PaymentDto) args[0];
        System.out.printf("%s started id=%s, status=%s%n", joinPoint.getSignature().getName(), payment.id(), payment.status());
    }

    @AfterReturning(
            pointcut = "@annotation(LogPayment))",
            returning = "payment")
    public void logPaymentFinished(JoinPoint joinPoint, PaymentDto payment) {
        System.out.printf("%s finished id=%s, status=%s%n", joinPoint.getSignature().getName(), payment.id(), payment.status());
    }

    @AfterThrowing(
            pointcut = "execution(* com.microservices.aop.service.ValidationService.validatePayment(*))",
            throwing = "exception")
    public void logPaymentValidationFailed(PaymentValidationException exception) {
        System.out.printf("PaymentDto has error: %s%n", exception);
    }

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = Instant.now();
        var retValue = joinPoint.proceed();
        var executionTime = Duration.between(startTime, Instant.now()).toMillis();
        System.out.println((String.format("Method %s executed in %dms", joinPoint.getSignature(), executionTime)));
        return retValue;
    }

}
