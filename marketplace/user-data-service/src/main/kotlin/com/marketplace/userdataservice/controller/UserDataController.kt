package com.marketplace.userdataservice.controller

import com.marketplace.userdataservice.model.dto.UserDataResponseDto
import com.marketplace.userdataservice.service.UserDataService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
class UserDataController(
    val userDataService: UserDataService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/data")
    fun getRandomUserData(): Mono<UserDataResponseDto> {
        log.info("Received getRandomUserData request")
        return userDataService.getRandomUserData()
    }
}