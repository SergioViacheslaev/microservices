package com.marketplace.userprofileservice.controller

import com.marketplace.userprofileservice.model.dto.UserProfileResponseDto
import com.marketplace.userprofileservice.service.UserDataService
import com.marketplace.userprofileservice.service.UserOrdersService
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/v1/users")
class UserProfileController(
    val userDataService: UserDataService,
    val userOrdersService: UserOrdersService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("{user_id}/profile")
    @Operation(summary = "Get user profile by user_id")
    fun getUserProfile(@PathVariable("user_id") userId: String): Mono<UserProfileResponseDto> {
        log.info("Received get user profile request for userId $userId")

        return userDataService.getUserDataByUserId(userId)
            .zipWith(userOrdersService.getUserOrdersByUserId(userId))
            .map {
                UserProfileResponseDto(userData = it.t1, userOrders = it.t2)
            }
    }
}