package dev.chaunm.commerceevolution.catalog.domain.event.brand;

import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record BrandRestoredEvent(
        BrandId brandId
) implements DomainEvent {
}
