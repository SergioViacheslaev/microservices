package com.marketplace.userdataservice.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RandomUserDataResponseDto(
    val id: Int,
    val uid: String,
    val password: String,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
    val username: String,
    val email: String,
    val avatar: String,
    val gender: String,
    @JsonProperty("phone_number")
    val phoneNumber: String,
    @JsonProperty("social_insurance_number")
    val socialInsuranceNumber: String,
    @JsonProperty("date_of_birth")
    val dateOfBirth: String,
    val employment: Employment,
    val address: Address,
    @JsonProperty("credit_card")
    val creditCard: CreditCard,
    val subscription: Subscription,
)

data class Address(
    val city: String,
    @JsonProperty("street_name")
    val streetName: String,
    @JsonProperty("street_address")
    val streetAddress: String,
    @JsonProperty("zip_code")
    val zipCode: String,
    val state: String,
    val country: String,
    val coordinates: Coordinates,
)

data class CreditCard(
    @JsonProperty("cc_number")
    val ccNumber: String
)

data class Employment(
    val title: String,
    @JsonProperty("key_skill")
    val keySkill: String
)

data class Subscription(
    val plan: String,
    val status: String,
    @JsonProperty("payment_method")
    val paymentMethod: String,
    val term: String
)

data class Coordinates(
    val lat: Double,
    val lng: Double
)
