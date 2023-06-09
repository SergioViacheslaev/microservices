package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static java.util.Objects.isNull;

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
    @Getter(AccessLevel.NONE)
    private EmployeePassportEntity passport;

    @OneToMany(cascade = ALL, mappedBy = "employee", fetch = FetchType.LAZY)
    @Getter(AccessLevel.NONE)
    private List<EmployeePhoneEntity> phones;

    @ManyToMany()
    @JoinTable(name = "employees_departments",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @Getter(AccessLevel.NONE)
    private List<EmployeeDepartmentEntity> departments;

    /**
     * In bidirectional association:
     * 1. For each Phone adds Employee (ManyToOne)
     * 2. For Employee adds Phones (OneToMany)
     *
     * @param phones entities
     */
    public void addPhones(List<EmployeePhoneEntity> phones) {
        phones.forEach(employeePhoneEntity -> employeePhoneEntity.setEmployee(this));
        this.setPhones(phones);
    }

    public EmployeePassportEntity getPassport() {
        return isNull(passport) ? EmployeePassportEntity.builder().build() : passport;
    }

    public List<EmployeePhoneEntity> getPhones() {
        return isNull(phones) ? Collections.emptyList() : phones;
    }

    public List<EmployeeDepartmentEntity> getDepartments() {
        return isNull(departments) ? Collections.emptyList() : departments;
    }
}
