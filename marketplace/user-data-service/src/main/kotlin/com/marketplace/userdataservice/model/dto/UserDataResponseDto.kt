package com.marketplace.userdataservice.model.dto

import java.util.*

data class UserDataResponseDto(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String,
)
