package dev.chaunm.commerceevolution.catalog.domain.event.variant;

import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record VariantDisabledEvent(
        ProductId productId,
        VariantId variantId
) implements DomainEvent {
}
