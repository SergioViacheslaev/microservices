package com.study.microservices.employeeservice.dao;

import com.study.microservices.employeeservice.dao.mapper.EmployeeEntityRowMapper;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeEntityJdbcTemplateDao implements EmployeeEntityDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<EmployeeEntity> findAll() {
        //with jdbcTemplate
        return jdbcTemplate.query("SELECT * FROM employee", new EmployeeEntityRowMapper());
    }
}
