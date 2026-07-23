package dev.chaunm.commerceevolution.payment.infrastructure.persistence.entity;

import dev.chaunm.commerceevolution.shared.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentEntity extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false, unique = true, updatable = false)
    private UUID orderId;

    @Column(nullable = false, updatable = false)
    private long amount;

    @Column(nullable = false)
    private String status;
}
