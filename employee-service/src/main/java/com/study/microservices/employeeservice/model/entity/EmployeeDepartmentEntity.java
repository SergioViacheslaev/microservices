package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
public class EmployeeDepartmentEntity {

    @Id
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    @ManyToMany
    @JoinTable(name = "employees_departments",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EmployeeEntity> employees;

}
