package com.study.microservices.employeeservice.model.entity;

import com.study.microservices.employeeservice.model.dto.PhoneType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phones")
public class EmployeePhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long phoneId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_type")
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmployeeEntity employee;

}
