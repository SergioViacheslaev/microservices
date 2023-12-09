package com.study.microservices.springbootredis.model.dto

import java.io.Serializable


/**
 *  DTO class, see
 *  [UserService.findAllUsers()][com.study.microservices.springbootredis.service.UserService.findAllUsers]
 */
data class UserDto(
    val name: String,
    val surName: String,
    val age: Int
) : Serializable
