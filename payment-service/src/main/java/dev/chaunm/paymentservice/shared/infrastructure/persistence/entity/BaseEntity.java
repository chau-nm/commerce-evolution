package dev.chaunm.paymentservice.shared.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    /**
     * updatable = false is load-bearing, not stylistic: every repository's save() maps a fresh
     * detached entity from the domain object on every call, and the domain model never carries
     * createdAt, so that fresh instance always has createdAt = null. Without this flag,
     * Hibernate includes created_at in every UPDATE statement and overwrites the real value
     * with null, which then fails the NOT NULL constraint the migration declares on
     * created_at. Excluding the column from UPDATE entirely — the standard JPA idiom for
     * immutable audit columns — sidesteps the problem regardless of what the mapper produces.
     */
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
}
