package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
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
//    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmployeeEntity employee;

}
