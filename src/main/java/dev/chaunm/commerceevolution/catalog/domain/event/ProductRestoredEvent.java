package dev.chaunm.commerceevolution.catalog.domain.event;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record ProductRestoredEvent(
        ProductId productId
) implements DomainEvent {
}
