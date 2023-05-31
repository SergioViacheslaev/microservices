package com.study.microservices.employeeservice.dao.mapper;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeEntityRowMapper implements RowMapper<EmployeeEntity> {

    @Override
    public EmployeeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return EmployeeEntity.builder()
                .employeeId(rs.getObject("employee_id", java.util.UUID.class))
                .employeeName(rs.getString("employee_name"))
                .employeeSurname(rs.getString("employee_surname"))
                .employeeBirthDate(rs.getTimestamp("employee_birth_date").toLocalDateTime().toLocalDate())
                .build();
    }
}
