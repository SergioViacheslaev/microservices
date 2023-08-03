package com.marketplace.userprofileservice.model.dto

import java.util.*

data class UserProfileResponseDto(
    val userData: UserData,
    val userOrders: List<UserOrder>
)

data class UserData(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String
)

data class UserOrder(
    val orderId: String,
    val title: String,
    val description: String,
    val price: String
)
