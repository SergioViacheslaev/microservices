package com.study.grpcserver.service

import com.study.grpc.GreetingServiceGrpc
import com.study.grpc.GreetingServiceOuterClass
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory


@GrpcService
class GreetingService : GreetingServiceGrpc.GreetingServiceImplBase() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun greeting(
        request: GreetingServiceOuterClass.GreetingRequest,
        responseObserver: StreamObserver<GreetingServiceOuterClass.GreetingResponse>
    ) {
        log.info("Received gRPC GreetingRequest $request")

        val greetingResponse = GreetingServiceOuterClass.GreetingResponse.newBuilder()
            .setGreeting("Hello ${request.name} from gRPC").build()

        responseObserver.onNext(greetingResponse)
        responseObserver.onCompleted()
    }
}

