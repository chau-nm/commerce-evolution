package dev.chaunm.commerceevolution.catalog.domain.model.variant;

import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.Money;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;

/**
 * Read-only projection of a {@link ProductVariant}. {@code Product} exposes variants through
 * this type so callers outside the aggregate (use cases, mappers) can read but never mutate a
 * variant directly — mutation is only possible through {@code Product}'s own methods, which
 * enforce its invariants (archived/deleted guards, domain events).
 */
public interface VariantView {
    VariantId getId();

    SKU getSku();

    String getName();

    boolean isActive();

    Money getPrice();
}
