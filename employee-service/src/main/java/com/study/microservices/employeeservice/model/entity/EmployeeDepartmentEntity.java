package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_department")
public class EmployeeDepartmentEntity {

    @Id
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

}
