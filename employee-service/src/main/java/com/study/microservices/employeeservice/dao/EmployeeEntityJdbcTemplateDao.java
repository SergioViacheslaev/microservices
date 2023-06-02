package com.study.microservices.employeeservice.dao;

import com.study.microservices.employeeservice.dao.mapper.EmployeeEntityRowMapper;
import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeEntityJdbcTemplateDao implements EmployeeEntityDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<EmployeeEntity> findAll() {
        log.info("EmployeeEntityJdbcTemplateDao findAll EmployeeEntity");

        //with jdbcTemplate
        return jdbcTemplate.query("SELECT * FROM employee", new EmployeeEntityRowMapper());
    }
}
