package com.marketplace.userordersservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserOrdersServiceApplication

fun main(args: Array<String>) {
    runApplication<UserOrdersServiceApplication>(*args)
}
