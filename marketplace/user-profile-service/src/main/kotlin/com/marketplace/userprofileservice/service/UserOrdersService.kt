package com.marketplace.userprofileservice.service

import com.marketplace.userprofileservice.model.dto.UserOrder
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UserOrdersService {

    private val log = LoggerFactory.getLogger(javaClass)
    private val userOrdersWebClient: WebClient = WebClient.create("http://localhost:8082/marketplace/api/v1/users")

    fun getUserOrdersByUserId(userId: String): Mono<List<UserOrder>> {
        return userOrdersWebClient.get()
            .uri("/{user_id}/orders", userId)
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<UserOrder>>() {})
            .switchIfEmpty(Mono.defer {
                log.info("User orders not found for userId $userId")
                Mono.empty()
            })
            .doOnNext { log.info("Received user orders $it for userId $userId") }
    }
}