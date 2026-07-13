package dev.chaunm.commerceevolution.authentication.infrastructure.persistence.entity;

import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Role;
import dev.chaunm.commerceevolution.authentication.domain.model.valueobject.Status;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class AccountEntity {

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

    private Instant createdAt;

    private Instant updatedAt;
}
