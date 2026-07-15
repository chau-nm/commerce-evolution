package dev.chaunm.commerceevolution.catalog.domain.event.product;

import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CategoryAssignedEvent(
        ProductId productId,
        CategoryId categoryId
) implements DomainEvent {
}
