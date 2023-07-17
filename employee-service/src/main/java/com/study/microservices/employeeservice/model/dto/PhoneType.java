package com.study.microservices.employeeservice.model.dto;

import com.study.microservices.employeeservice.exception.EmployeePhoneTypeFormatException;

import java.util.Map;

public enum PhoneType {
    WORK("Рабочий"),
    HOME("Домашний");

    private static final Map<String, PhoneType> phoneTypeNames = Map.of(
            WORK.name, WORK,
            HOME.name, HOME);

    private final String name;

    PhoneType(String name) {
        this.name = name;
    }

    public static PhoneType getPhoneTypeFromString(String type) {
        PhoneType phoneType = phoneTypeNames.get(type);
        if (phoneType == null) {
            throw new EmployeePhoneTypeFormatException(String.format("Указан несуществующий тип телефона: %s", type));
        }
        return phoneType;
    }

    public String getName() {
        return name;
    }

}
