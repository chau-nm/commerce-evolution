package dev.chaunm.commerceevolution.cart.domain.event;

import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CartClearedEvent(
        CartId cartId
) implements DomainEvent {
}
