package com.microservices.aop.service;

import com.microservices.aop.exception.PaymentValidationException;
import com.microservices.aop.model.PaymentDto;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public void validatePayment(PaymentDto paymentDto) {
        if (paymentDto == null) {
            throw new PaymentValidationException("Payment can't be NULL");
        }
    }
}
