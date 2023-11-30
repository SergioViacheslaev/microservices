package com.study.microservices.springbootredis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootRedisApplication

fun main(args: Array<String>) {
    runApplication<SpringBootRedisApplication>(*args)
}
