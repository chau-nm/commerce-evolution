package dev.chaunm.commerceevolution.catalog.domain.event.category;

import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CategoryDeletedEvent(
        CategoryId categoryId
) implements DomainEvent {
}
