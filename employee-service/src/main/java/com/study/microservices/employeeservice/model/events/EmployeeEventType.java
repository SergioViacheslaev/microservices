package com.study.microservices.employeeservice.model.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmployeeEventType {
    CREATE("Создан"),
    DELETE("Удален"),
    UPDATE("Обновлен");

    private final String name;
}
