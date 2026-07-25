package dev.chaunm.commerceevolution.cart.domain.event;

import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartItemId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CartItemRemovedEvent(
        CartId cartId,
        CartItemId cartItemId,
        VariantId variantId
) implements DomainEvent {
}
