package com.marketplace.userordersservice.controller

import com.marketplace.userordersservice.model.dto.UserOrderResponseDto
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
class UserOrdersController(
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("{user_id}/orders")
    @Operation(summary = "Get user order by user_id")
    fun findOrdersByUserId(@PathVariable("user_id") userId: String): Mono<List<UserOrderResponseDto>> {
        log.info("Received findOrders by userId $userId request")
        return Mono.just(
            listOf(
                UserOrderResponseDto(
                    "1",
                    "Tea bags",
                    "Black tea",
                    "100"
                ), UserOrderResponseDto(
                    "2",
                    "Apple Macbook Air 15",
                    "Laptop",
                    "250000"
                )
            )
        )
    }


}