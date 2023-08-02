package com.marketplace.userdataservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "user_data")
data class UserDataEntity(
    @Id
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String,
)
