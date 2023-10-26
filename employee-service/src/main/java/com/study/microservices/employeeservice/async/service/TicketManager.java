package com.study.microservices.employeeservice.async.service;

import com.study.microservices.employeeservice.exception.SseAsyncTimeoutException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.study.microservices.employeeservice.util.constants.AsyncConstants.SSE_CONNECTION_TIMEOUT_MS;

@Slf4j
@Component
public class TicketManager {

    //todo: remove emitter from map, after client receives data from SSE connection
    private final Map<String, SseEmitter> ticketToEmitters = new ConcurrentHashMap<>();

    public String createTicket() {
        // Each session/connection requires a new emitter. Timeout to prevent long lived connections.
        val emitter = new SseEmitter(SSE_CONNECTION_TIMEOUT_MS);
        val ticket = dispenseTicket();
        ticketToEmitters.put(ticket, emitter);
        emitter.onTimeout(() -> {
            throw new SseAsyncTimeoutException("SSE async timeout");
        });
        return ticket;
    }

    public SseEmitter getMappedSseEmitterForTicket(String ticket) {
        return ticketToEmitters.get(ticket);
    }

    private synchronized String dispenseTicket() {
        return UUID.randomUUID().toString();
    }

}
