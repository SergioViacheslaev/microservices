package com.study.grpcclient.service

import com.study.grpc.GreetingServiceGrpc.GreetingServiceBlockingStub
import com.study.grpc.GreetingServiceOuterClass.GreetingRequest
import com.study.grpc.GreetingServiceOuterClass.GreetingResponse
import io.grpc.StatusRuntimeException
import net.devh.boot.grpc.client.inject.GrpcClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class GrpcClientService {
    private val log = LoggerFactory.getLogger(javaClass)

    @GrpcClient("local-grpc-server")
    private lateinit var greetingStub: GreetingServiceBlockingStub

    fun getGreeting(name: String): String {
        return try {
            val response: GreetingResponse = greetingStub.greeting(GreetingRequest.newBuilder().setName(name).build())
            response.greeting
        } catch (e: StatusRuntimeException) {
            log.error("Failed with $e.status.code.name")
            throw e
        }
    }
}
