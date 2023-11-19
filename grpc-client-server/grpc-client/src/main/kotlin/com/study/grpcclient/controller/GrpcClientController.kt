package com.study.grpcclient.controller

import com.study.grpcclient.service.GrpcClientService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/greeting")
class GrpcClientController(
    val grpcClientService: GrpcClientService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping()
    fun getGreeting(
        @RequestParam(defaultValue = "Michael")
        name: String
    ): String {
        log.info("Received getGreeting request: $name")
        return grpcClientService.getGreeting(name)
    }

}