package com.study.microservices.employeeservice.repo.projection;

import java.util.UUID;

public interface EmployeeView {
    UUID getId();

    String getName();

    String getSurname();
}
