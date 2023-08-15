package com.microservices.saga.ordersservice.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record OrderCreateRequestDto(
        @NotBlank(message = "Order productId is a required field")
        String productId,

        @Min(value = 1, message = "Quantity cannot be lower than 1")
        @Max(value = 5, message = "Quantity cannot be larger than 5")
        int quantity,

        @NotBlank(message = "Order addressId is a required field")
        String addressId
) {
}
