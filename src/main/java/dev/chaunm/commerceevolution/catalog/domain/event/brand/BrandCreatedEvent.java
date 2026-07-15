package dev.chaunm.commerceevolution.catalog.domain.event.brand;

import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandName;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record BrandCreatedEvent(
        BrandId brandId,
        BrandName name
) implements DomainEvent {
}
