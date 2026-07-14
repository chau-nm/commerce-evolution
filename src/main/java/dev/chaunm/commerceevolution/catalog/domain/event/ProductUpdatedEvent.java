package dev.chaunm.commerceevolution.catalog.domain.event;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.Slug;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record ProductUpdatedEvent(
        ProductId productId,
        ProductName name,
        Slug slug
) implements DomainEvent {
}
