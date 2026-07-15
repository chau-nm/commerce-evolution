package dev.chaunm.commerceevolution.inventory.infrastructure.persistence.entity;

import dev.chaunm.commerceevolution.shared.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
