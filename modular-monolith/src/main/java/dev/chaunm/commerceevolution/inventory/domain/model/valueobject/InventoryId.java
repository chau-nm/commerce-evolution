package dev.chaunm.commerceevolution.inventory.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record InventoryId(UUID value) {

    public InventoryId {
        Objects.requireNonNull(value, "InventoryId cannot be null");
    }

    public static InventoryId generate() {
        return new InventoryId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
