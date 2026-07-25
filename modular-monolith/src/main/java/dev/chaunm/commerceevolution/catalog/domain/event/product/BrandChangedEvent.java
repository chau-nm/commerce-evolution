package dev.chaunm.commerceevolution.catalog.domain.event.product;

import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record BrandChangedEvent(
        ProductId productId,
        BrandId brandId
) implements DomainEvent {
}
