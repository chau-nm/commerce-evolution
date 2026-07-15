package dev.chaunm.commerceevolution.cart.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Cart's own identifier for a variant, deliberately decoupled from catalog's
 * VariantId type — Cart must never depend on the Product aggregate.
 */
public record VariantId(UUID value) {

    public VariantId {
        Objects.requireNonNull(value, "VariantId cannot be null");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
