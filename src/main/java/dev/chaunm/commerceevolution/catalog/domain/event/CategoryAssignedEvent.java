package dev.chaunm.commerceevolution.catalog.domain.event;

import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.ProductId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CategoryAssignedEvent(
        ProductId productId,
        CategoryId categoryId
) implements DomainEvent {
}
