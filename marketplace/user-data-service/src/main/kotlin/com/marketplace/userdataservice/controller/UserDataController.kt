package com.marketplace.userdataservice.controller

import com.marketplace.userdataservice.model.dto.RandomUserDataResponseDto
import com.marketplace.userdataservice.model.dto.UserDataResponseDto
import com.marketplace.userdataservice.service.UserDataService
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
class UserDataController(
    val userDataService: UserDataService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/random")
    @Operation(summary = "Get random user data")
    fun getRandomUserData(): Mono<RandomUserDataResponseDto> {
        log.info("Received getRandomUserData request")
        return userDataService.getRandomUserAndSaveData()
    }

    @GetMapping("/data")
    @Operation(summary = "Get all users data")
    fun getAllUsersData(): Flux<UserDataResponseDto> {
        log.info("Received getAllUsersData request")
        return userDataService.getAllUsersData()
    }

    @GetMapping("{user_id}/data")
    @Operation(summary = "Get user data by user_id")
    fun getUserDataById(@PathVariable("user_id") userId: String): Mono<UserDataResponseDto> {
        log.info("Received getUserData by Id $userId request")
        return userDataService.getUserDataById(userId)
    }

    @GetMapping("/data/email/{user_email}")
    @Operation(summary = "Get user data by user_email")
    fun getUserDataByEmail(@PathVariable("user_email") userEmail: String): Mono<UserDataResponseDto> {
        log.info("Received getUserData by email $userEmail request")
        return userDataService.getUserDataByEmail(userEmail)
    }

}