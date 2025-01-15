package com.microservices.aop.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Платеж
 *
 * @param id             id платежа
 * @param payerAccount   счет плательщика
 * @param paymentAccount счет получателя
 * @param amount         денежная сумма
 * @param description    описание платежа
 */
public record PaymentDto(UUID id, String payerAccount, String paymentAccount,
                         BigDecimal amount, String description, PaymentStatus status) {

    public PaymentDto withStatus(PaymentStatus status) {
        return new PaymentDto(id, payerAccount, paymentAccount, amount, description, status);
    }
}
