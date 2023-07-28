package com.study.microservices.employeeservice.service.listener;

import com.study.microservices.employeeservice.model.events.EmployeeEvent;
import com.study.microservices.employeeservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EmployeeEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    public void processEvent(EmployeeEvent event) {
        notificationService.sendMessage(event.eventType(),event.eventPayload());
    }
}
