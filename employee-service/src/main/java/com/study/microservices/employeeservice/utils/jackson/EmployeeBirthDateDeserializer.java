package com.study.microservices.employeeservice.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.study.microservices.employeeservice.exception.EmployeeBirthDateFormatException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EmployeeBirthDateDeserializer extends StdDeserializer<LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public EmployeeBirthDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            return LocalDate.parse(p.readValueAs(String.class), formatter);
        } catch (DateTimeParseException e) {
            throw new EmployeeBirthDateFormatException("Неверный формат даты рождения сотрудника, правильный yyyy-MM-dd", e);
        }
    }

}