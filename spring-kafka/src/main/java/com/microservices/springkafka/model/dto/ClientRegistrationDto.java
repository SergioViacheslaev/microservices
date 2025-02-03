package com.microservices.springkafka.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Регистрационные данные нового клиента
 */
public record ClientRegistrationDto(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("middle_name")
        String middleName,
        @JsonProperty("email")
        String email,
        @JsonProperty("gender")
        String gender,
        @JsonProperty("ip_address")
        String ipAddress
) {

}
