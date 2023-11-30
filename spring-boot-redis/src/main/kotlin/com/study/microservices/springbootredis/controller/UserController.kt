package com.study.microservices.springbootredis.controller

import com.study.microservices.springbootredis.model.dto.UserDto
import com.study.microservices.springbootredis.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): List<UserDto> {
        return userService.findAllUsers()
    }

}