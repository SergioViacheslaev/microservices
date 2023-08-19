package com.microservices.saga.ordersservice.util;

import com.microservices.saga.common.dto.PaymentDetails;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MockDataUtils {

    public static PaymentDetails createPaymentDetails() {
//        throw new RuntimeException("Couldn't fetch paymentDetails"); //test saga rollback
        return PaymentDetails.builder()
                .cardNumber("42352 422332")
                .name("Alex")
                .validUntilMonth(11)
                .validUntilYear(2030)
                .cvv("123")
                .build();
    }
}
