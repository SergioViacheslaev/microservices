package com.marketplace.userdataservice.repository

import com.marketplace.userdataservice.model.entity.UserDataEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.*

interface UserDataRepository : ReactiveMongoRepository<UserDataEntity, UUID> {

    fun findByEmail(email: String): Mono<UserDataEntity>
}

