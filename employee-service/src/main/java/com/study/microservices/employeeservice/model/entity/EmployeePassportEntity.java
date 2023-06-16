package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passports")
public class EmployeePassportEntity {

    @Id
    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "passport_number")
    private Long passportNumber;

    @Column(name = "registration_address")
    private String registrationAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "employee_id")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmployeeEntity employee;

}
