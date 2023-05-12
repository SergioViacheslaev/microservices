package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_passport")
public class EmployeePassportEntity {

    @Id
    @Column(name = "passport_id")
    private Long passportId;

    @Column(name = "registration_address")
    private String registrationAddress;

    @Column(name = "birth_date")
    private LocalDate birthDate;
}
