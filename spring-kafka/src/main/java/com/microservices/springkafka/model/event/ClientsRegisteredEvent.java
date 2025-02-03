package com.microservices.springkafka.model.event;


import com.microservices.springkafka.model.dto.ClientRegisteredDto;

import java.util.List;

/**
 * Содержит список dto, сохраненных в БД клиентов
 */
public record ClientsRegisteredEvent(List<ClientRegisteredDto> registeredClients) {
}
