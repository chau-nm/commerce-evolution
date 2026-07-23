package dev.chaunm.commerceevolution.inventory.domain.model;

import dev.chaunm.commerceevolution.inventory.domain.event.StockAdjustedEvent;
import dev.chaunm.commerceevolution.inventory.domain.event.StockDeductedEvent;
import dev.chaunm.commerceevolution.inventory.domain.event.StockReleasedEvent;
import dev.chaunm.commerceevolution.inventory.domain.event.StockReservedEvent;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientAvailableStockException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientReservedStockException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.InventoryId;
import dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

@Getter
public class Inventory extends AggregateRoot {

    private final InventoryId id;
    private final VariantId variantId;
    private int availableQuantity;
    private int reservedQuantity;

    public Inventory(
            InventoryId id,
            VariantId variantId,
            int availableQuantity,
            int reservedQuantity
    ) {
        this.id = id;
        this.variantId = variantId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
    }

    public void adjustStock(int quantityDelta) {
        int newAvailableQuantity = this.availableQuantity + quantityDelta;
        if (newAvailableQuantity < 0) {
            throw new InvalidQuantityException(newAvailableQuantity);
        }

        this.availableQuantity = newAvailableQuantity;
        registerEvent(new StockAdjustedEvent(this.id, this.variantId, quantityDelta, this.availableQuantity));
    }

    public void reserve(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
        if (quantity > this.availableQuantity) {
            throw new InsufficientAvailableStockException(this.availableQuantity, quantity);
        }

        this.availableQuantity -= quantity;
        this.reservedQuantity += quantity;
        registerEvent(new StockReservedEvent(this.id, this.variantId, quantity, this.availableQuantity));
    }

    public void release(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
        if (quantity > this.reservedQuantity) {
            throw new InsufficientReservedStockException(this.reservedQuantity, quantity);
        }

        this.reservedQuantity -= quantity;
        this.availableQuantity += quantity;
        registerEvent(new StockReleasedEvent(this.id, this.variantId, quantity, this.availableQuantity));
    }

    public void deduct(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
        if (quantity > this.reservedQuantity) {
            throw new InsufficientReservedStockException(this.reservedQuantity, quantity);
        }

        this.reservedQuantity -= quantity;
        registerEvent(new StockDeductedEvent(this.id, this.variantId, quantity, this.reservedQuantity));
    }

}
