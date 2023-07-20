package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
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

    @Column(name = "monthly_salary",precision = 10, scale = 3)
    private BigDecimal monthlySalary;

    @Column(name = "payroll_account")
    private String payrollAccount;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private EmployeePassportEntity passport;

    @OneToMany(cascade = ALL, mappedBy = "employee", fetch = FetchType.LAZY)
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
    private Set<EmployeeDepartmentEntity> departments = new HashSet<>();

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
        employeeDepartment.getEmployees().add(this);
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
