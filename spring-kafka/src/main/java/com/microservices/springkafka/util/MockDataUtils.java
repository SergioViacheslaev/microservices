package com.microservices.springkafka.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.springkafka.model.dto.ClientRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class MockDataUtils {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Читает файл MOCK_DATA.json и возвращает список Dto клиентов для регистрации
     */
    public List<ClientRegistrationDto> getMockClients() {
        TypeReference<List<ClientRegistrationDto>> jacksonTypeReference = new TypeReference<>() {};
        List<ClientRegistrationDto> mockClients;
        try {
            mockClients = mapper.readValue(new File("src/main/resources/MOCK_DATA.json"), jacksonTypeReference);
        } catch (IOException e) {
            log.error("Error parsing JSON file with mock clients", e);
            throw new RuntimeException(e);
        }

        return mockClients;
    }
}
