package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static jakarta.persistence.CascadeType.*;
import static java.util.Objects.isNull;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class EmployeeEntity extends AuditedEntity {

    @Id
    //org.hibernate.id.UUIDGenerator generates id internally
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private UUID id;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "employee_surname")
    private String surname;

    @Column(name = "employee_birth_date")
    private LocalDate birthDate;

    @Column(name = "monthly_salary", precision = 10, scale = 3)
    private BigDecimal monthlySalary;

    @Column(name = "payroll_account")
    private String payrollAccount;

    @OneToOne(mappedBy = "employee", cascade = ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
//    @LazyToOne(LazyToOneOption.NO_PROXY)
    private EmployeePassportEntity passport;

    @OneToMany(cascade = ALL, mappedBy = "employee", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<EmployeePhoneEntity> phones;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            PERSIST,
            MERGE
    })
    // fixes N+1 problem, first select all departments, then joins with all fetched employees
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "employees_departments",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @ToString.Exclude
    private Set<EmployeeDepartmentEntity> departments;

    /**
     * In bidirectional association:
     * 1. For each Phone add Employee (ManyToOne)
     * 2. For Employee add Phones (OneToMany)
     *
     * @param phones entities
     */
    public void addPhones(List<EmployeePhoneEntity> phones) {
        phones.forEach(employeePhoneEntity -> employeePhoneEntity.setEmployee(this));
        this.setPhones(phones);
    }

    public void addPassport(EmployeePassportEntity passport) {
        this.passport = passport;
        passport.setEmployee(this);
    }

    public void addDepartment(EmployeeDepartmentEntity employeeDepartment) {
        if (departments == null) {
            departments = new HashSet<>();
        }
        departments.add(employeeDepartment);
    }

    public List<EmployeePhoneEntity> getPhones() {
        return isNull(phones) ? new ArrayList<>() : phones;
    }

    public EmployeePassportEntity getPassport() {
        return isNull(passport) ? EmployeePassportEntity.builder().build() : passport;
    }

    public Set<EmployeeDepartmentEntity> getDepartments() {
        return isNull(departments) ? new HashSet<>() : departments;
    }

}
