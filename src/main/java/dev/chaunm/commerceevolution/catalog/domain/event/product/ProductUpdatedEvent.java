package dev.chaunm.commerceevolution.catalog.domain.event.product;

import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record ProductUpdatedEvent(
        ProductId productId,
        ProductName name,
        Slug slug
) implements DomainEvent {
}
