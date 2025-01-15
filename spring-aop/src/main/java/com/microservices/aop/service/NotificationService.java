package com.microservices.aop.service;

import com.microservices.aop.model.PaymentDto;
import org.springframework.stereotype.Service;

/**
 * Сервис уведомлений
 */
@Service
public class NotificationService {

    /**
     * Уведомляет ФСБ о подозрительном платеже
     */
    public void notifyFSB(PaymentDto paymentDto) {
        System.out.printf("Sending suspicious payment %s to FSB%n", paymentDto.id());
    }
}
