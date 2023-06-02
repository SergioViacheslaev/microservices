package com.study.microservices.employeeservice.dao;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeEntityHibernateDao implements EmployeeEntityDao {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<EmployeeEntity> findAll() {
        log.info("EmployeeEntityHibernateDao findAll EmployeeEntity");

        try (var entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<EmployeeEntity> query = entityManager.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity.class);
            return query.getResultList();
        }
    }
}
