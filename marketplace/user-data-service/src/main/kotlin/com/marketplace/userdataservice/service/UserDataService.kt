package com.marketplace.userdataservice.service

import com.marketplace.userdataservice.model.dto.UserDataResponseDto
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UserDataService(val webClient: WebClient) {

    fun getRandomUserData(): Mono<UserDataResponseDto> {
        return webClient
            .get()
            .uri("/users")
            .retrieve()
            .bodyToMono(UserDataResponseDto::class.java)
    }
}