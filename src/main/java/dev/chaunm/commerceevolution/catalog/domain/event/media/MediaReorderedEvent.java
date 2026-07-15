package dev.chaunm.commerceevolution.catalog.domain.event.media;

import dev.chaunm.commerceevolution.catalog.domain.model.media.valueobject.MediaId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

import java.util.List;

public record MediaReorderedEvent(
        ProductId productId,
        List<MediaId> mediaIds
) implements DomainEvent {
}
