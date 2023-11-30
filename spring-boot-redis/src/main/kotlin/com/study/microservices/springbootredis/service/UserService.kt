package com.study.microservices.springbootredis.service

import com.study.microservices.springbootredis.model.dto.UserDto
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Cacheable("users")
    fun findAllUsers(): List<UserDto> {
        simulateLongRunningDbQuery()

        return listOf(
            UserDto("Foo", "Bar", 22),
            UserDto("Alex", "Mops", 33)
        )
    }

    private fun simulateLongRunningDbQuery() {
        logger.info("long running DB query...")
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}