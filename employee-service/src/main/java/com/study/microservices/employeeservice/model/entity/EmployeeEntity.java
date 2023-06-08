package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "employees")
public class EmployeeEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private UUID Id;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "employee_surname")
    private String surname;

    @Column(name = "employee_birth_date")
    private LocalDate birthDate;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    private EmployeePassportEntity passport;

    @OneToMany(cascade = ALL, mappedBy = "employee", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    private List<EmployeePhoneEntity> phones;

    public EmployeePassportEntity getPassport() {
        return Objects.isNull(passport) ? EmployeePassportEntity.builder().build() : passport;
    }

    public List<EmployeePhoneEntity> getPhones() {
        return Objects.isNull(phones) ? Collections.emptyList() : phones;
    }
}
