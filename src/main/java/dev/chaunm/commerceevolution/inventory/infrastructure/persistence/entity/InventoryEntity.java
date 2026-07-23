package dev.chaunm.commerceevolution.inventory.infrastructure.persistence.entity;

import dev.chaunm.commerceevolution.shared.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "inventories")
@Getter
@Setter
public class InventoryEntity extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "variant_id", nullable = false, unique = true, updatable = false)
    private UUID variantId;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity;

    /**
     * Concurrent reserve/deduct/release calls on the same row are a real oversell risk
     * (check-then-mutate on plain ints); JPA optimistic locking makes a conflicting concurrent
     * update fail fast with ObjectOptimisticLockingFailureException instead of silently
     * clobbering the loser's change.
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}
