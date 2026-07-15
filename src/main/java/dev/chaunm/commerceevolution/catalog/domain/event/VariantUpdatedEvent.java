package dev.chaunm.commerceevolution.catalog.domain.event;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record VariantUpdatedEvent(
        ProductId productId,
        VariantId variantId,
        SKU sku,
        String name
) implements DomainEvent {
}
