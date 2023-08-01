package com.marketplace.userprofileservice.controller

import com.marketplace.userprofileservice.model.dto.UserProfileDto
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalTime

@RestController
@RequestMapping("/api/v1/users")
class UserProfileController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/profile", produces = [TEXT_EVENT_STREAM_VALUE])
    fun getAllUsersProfiles(): Flux<UserProfileDto> {
        logger.info("Received get all users profiles request")

        return Flux.interval(Duration.ofSeconds(1))
            .map { UserProfileDto("Foo", "Bar", "AvatarLink" + LocalTime.now()) }
    }

    @GetMapping("{user_id}/profile")
    fun getUserProfile(@PathVariable("user_id") userId: String): Mono<UserProfileDto> {
        logger.info("Received get user $userId profile request")

        return Mono.just(
            UserProfileDto(
                "Foo",
                "Bar",
                "AvatarLink"
            )
        )
    }
}