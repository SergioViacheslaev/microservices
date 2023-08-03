package com.marketplace.userordersservice.model.dto

data class UserOrderResponseDto(
    val orderId: String,
    val title: String,
    val description: String,
    val price: String
)