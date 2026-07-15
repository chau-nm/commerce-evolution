package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Order's own identifier for a variant, deliberately decoupled from catalog's
 * VariantId type — Order must never depend on the Product aggregate.
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
