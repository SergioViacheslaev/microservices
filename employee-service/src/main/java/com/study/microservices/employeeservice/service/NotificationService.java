package com.study.microservices.employeeservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void sendMessage(String evenType, String eventPayload) {
        log.info("Sending message: {} {}", evenType, eventPayload);
    }
}
