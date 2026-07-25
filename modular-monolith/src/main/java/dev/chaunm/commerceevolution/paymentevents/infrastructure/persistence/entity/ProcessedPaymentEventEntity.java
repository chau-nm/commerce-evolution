package dev.chaunm.commerceevolution.paymentevents.infrastructure.persistence.entity;

import dev.chaunm.commerceevolution.shared.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Idempotency ledger for the payment-outcome webhook: a payment can only ever settle once (its
 * own state machine forbids PENDING -> PAID -> PAID or PENDING -> FAILED -> FAILED), so
 * {@code paymentId} alone is a safe dedup key for "have I already reacted to this outcome" —
 * across normal retries (resilience4j on the sender side) and any accidental redelivery.
 */
@Entity
@Table(name = "processed_payment_events")
@Getter
@Setter
@NoArgsConstructor
public class ProcessedPaymentEventEntity extends BaseEntity {

    @Id
    @Column(name = "payment_id", nullable = false, updatable = false)
    private UUID paymentId;

    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @Column(nullable = false, updatable = false)
    private String status;

    public ProcessedPaymentEventEntity(UUID paymentId, UUID orderId, String status) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
    }
}
