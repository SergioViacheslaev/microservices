package com.study.microservices.employeeservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AuditedEntity {

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createdOn;

    @Column(name = "created_by")
    @CreatedBy
    protected String createdBy;

    @Column(name = "updated_on")
    @LastModifiedDate
    protected LocalDateTime updatedOn;

    @Column(name = "updated_by")
    @LastModifiedBy
    protected String updatedBy;
}
