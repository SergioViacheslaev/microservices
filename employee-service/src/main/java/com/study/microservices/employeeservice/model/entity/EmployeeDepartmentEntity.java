package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
public class EmployeeDepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id")
    private UUID id;

    @Column(name = "department_name")
    private String departmentName;

    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EmployeeEntity> employees;

    @Version
    private Integer version;

}
