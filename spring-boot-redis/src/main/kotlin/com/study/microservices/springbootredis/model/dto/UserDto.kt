package com.study.microservices.springbootredis.model.dto

import java.io.Serializable

/**
 *  Data class throws Exception with @Cacheable
 *  [findAllUsers()][com.study.microservices.springbootredis.service.UserService.findAllUsers]
 */
open class UserDto(
    val name: String,
    val surName: String,
    val age: Int
) : Serializable
