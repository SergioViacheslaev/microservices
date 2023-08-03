package com.marketplace.userprofileservice.service

import com.marketplace.userprofileservice.exception.UserDataNotFoundException
import com.marketplace.userprofileservice.model.dto.UserData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UserDataService {

    private val log = LoggerFactory.getLogger(javaClass)
    private val userDataWebClient: WebClient = WebClient.create("http://localhost:8081/marketplace/api/v1/users")

    fun getUserDataByUserId(userId: String): Mono<UserData> {
        return userDataWebClient.get()
            .uri("/{user_id}/data", userId)
            .retrieve()
            .bodyToMono(UserData::class.java)
            .switchIfEmpty(Mono.error {
                log.error("User data not found for userId $userId")
                UserDataNotFoundException()
            })
            .doOnNext { log.info("Received user data $it for userId $userId") }
    }
}