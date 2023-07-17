package com.study.microservices.employeeservice.dao;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class EmployeeEntityHibernateDao implements EmployeeEntityDao {

    //@PersistenceContext redundant or must annotation, check it !
    private final EntityManager entityManager;

    //Container-Managed EntityManager
    @Override
    public List<EmployeeEntity> findAll() {
        log.info("EmployeeEntityHibernateDao findAll EmployeeEntity");

        TypedQuery<EmployeeEntity> query = entityManager.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity.class);
        return query.getResultList();
    }

//    //Application-Managed EntityManager
//    @Override
//    public List<EmployeeEntity> findAll() {
//        log.info("EmployeeEntityHibernateDao findAll EmployeeEntity");
//
//        try (var entityManager = entityManagerFactory.createEntityManager()) {
//            TypedQuery<EmployeeEntity> query = entityManager.createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity.class);
//            return query.getResultList();
//        }
//    }
}
