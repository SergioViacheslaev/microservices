package com.study.microservices.employeeservice.model.events;

public record EmployeeEvent(
        String eventType,
        String eventPayload) {
}
