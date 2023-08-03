package com.marketplace.userdataservice.service

import com.marketplace.userdataservice.model.dto.RandomUserDataResponseDto
import com.marketplace.userdataservice.model.dto.UserDataResponseDto
import com.marketplace.userdataservice.model.entity.UserDataEntity
import com.marketplace.userdataservice.repository.UserDataRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class UserDataService(
    val webClient: WebClient, val userDataRepository: UserDataRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getRandomUserAndSaveData(): Mono<RandomUserDataResponseDto> {
        return webClient.get()
            .uri("/users")
            .retrieve()
            .bodyToMono(RandomUserDataResponseDto::class.java)
            .onErrorMap { error -> RuntimeException("Error getting random user data from external service", error) }
            .doOnNext { log.info("Received random user: $it") }
            .zipWhen {
                userDataRepository.save(
                    UserDataEntity(
                        UUID.randomUUID(), it.firstName, it.lastName, it.email, it.avatar
                    )
                ).doOnNext { log.info("Saved user data entity: $it") }
            }
            .map {
                val userDataResponseDto = it.t1
                userDataResponseDto
            }
    }

    fun getAllUsersData(): Flux<UserDataResponseDto> {
        return userDataRepository.findAll().map {
            UserDataResponseDto(
                it.id, it.firstName, it.lastName, it.email, it.avatar
            )
        }
    }

    fun getUserDataById(id: String): Mono<UserDataResponseDto> {
        return userDataRepository.findById(UUID.fromString(id)).map {
            UserDataResponseDto(
                it.id, it.firstName, it.lastName, it.email, it.avatar
            )
        }
    }

    fun getUserDataByEmail(userEmail: String): Mono<UserDataResponseDto> {
        return userDataRepository.findByEmail(userEmail).map {
            UserDataResponseDto(
                it.id, it.firstName, it.lastName, it.email, it.avatar
            )
        }

    }
}