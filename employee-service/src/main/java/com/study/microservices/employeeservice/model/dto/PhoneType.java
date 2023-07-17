package com.study.microservices.employeeservice.model.dto;

import com.study.microservices.employeeservice.exception.EmployeePhoneTypeFormatException;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum PhoneType {
    WORK("Рабочий"),
    HOME("Домашний");

    private static final Map<String, PhoneType> phoneTypeNames = Stream.of(PhoneType.values())
            .collect(toMap(PhoneType::getName, identity()));

    private final String name;

    PhoneType(String name) {
        this.name = name;
    }

    public static PhoneType getPhoneTypeFromString(String typeName) {
        PhoneType phoneType = phoneTypeNames.get(typeName);
        if (phoneType == null) {
            throw new EmployeePhoneTypeFormatException(String.format("Указан несуществующий тип телефона: %s", typeName));
        }
        return phoneType;
    }

    public String getName() {
        return name;
    }

}
