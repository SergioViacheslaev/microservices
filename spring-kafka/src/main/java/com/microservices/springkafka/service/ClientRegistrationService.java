package com.microservices.springkafka.service;

import com.microservices.springkafka.model.dto.ClientRegisteredDto;
import com.microservices.springkafka.model.dto.ClientRegistrationDto;
import com.microservices.springkafka.model.entity.ClientEntity;
import com.microservices.springkafka.model.event.ClientsRegisteredEvent;
import com.microservices.springkafka.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сохраняет клиентов в БД,
 * направляет список обогащенных id клиентов в ClientRegisteredNotificationService для последюущей отправки в Кафку
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRegistrationService {

    private final ClientRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void registerClients(List<ClientRegistrationDto> clients) {
        final List<ClientEntity> clientEntities = getClientEntitiesFromDto(clients);
        final List<ClientEntity> savedClientsEntities = repository.saveAll(clientEntities);
        final List<ClientRegisteredDto> registeredClientsDto = getRegisteredClientsDto(savedClientsEntities);
        eventPublisher.publishEvent(new ClientsRegisteredEvent(registeredClientsDto));
    }

    private List<ClientEntity> getClientEntitiesFromDto(List<ClientRegistrationDto> clients) {
        return clients.stream().map(client -> ClientEntity.builder()
                                                          .firstName(client.firstName())
                                                          .lastName(client.lastName())
                                                          .middleName(client.middleName())
                                                          .gender(client.gender())
                                                          .email(client.email())
                                                          .ipAddress(client.ipAddress())
                                                          .build()).toList();
    }

    private List<ClientRegisteredDto> getRegisteredClientsDto(List<ClientEntity> clients) {
        return clients.stream().map(client -> new ClientRegisteredDto(
                client.getId(), client.getFirstName(), client.getLastName(), client.getMiddleName(), client.getEmail(),
                client.getGender(), client.getIpAddress())).toList();
    }

}
