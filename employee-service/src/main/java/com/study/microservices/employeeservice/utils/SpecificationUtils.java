package com.study.microservices.employeeservice.utils;

import com.study.microservices.employeeservice.model.entity.EmployeeEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class SpecificationUtils {

    public static Specification<EmployeeEntity> hasName(String employeeName) {
        return (employeeEntity, cq, cb) -> cb.equal(employeeEntity.get("name"), employeeName);
    }

    public static Specification<EmployeeEntity> hasSurname(String employeeSurname) {
        return (employeeEntity, cq, cb) -> cb.equal(employeeEntity.get("surname"), employeeSurname);
    }
}
