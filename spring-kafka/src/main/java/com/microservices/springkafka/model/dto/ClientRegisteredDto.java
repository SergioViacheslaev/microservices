package com.microservices.springkafka.model.dto;

/**
 * Зарегистрированный клиент с присвоенным id
 */
public record ClientRegisteredDto(
        long id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String gender,
        String ipAddress
) {

}
