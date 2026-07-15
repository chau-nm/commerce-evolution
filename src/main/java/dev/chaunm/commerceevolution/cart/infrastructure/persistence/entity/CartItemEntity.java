package dev.chaunm.commerceevolution.cart.infrastructure.persistence.entity;

import dev.chaunm.commerceevolution.shared.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItemEntity extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false, updatable = false)
    private CartEntity cart;

    @Column(name = "variant_id", nullable = false, updatable = false)
    private UUID variantId;

    @Column(nullable = false)
    private int quantity;
}
