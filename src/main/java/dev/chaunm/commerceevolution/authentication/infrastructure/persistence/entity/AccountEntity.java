package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.entity;

import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Role;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Status;
import dev.chaunm.commerceevolution.shared.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class AccountEntity extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;
}
