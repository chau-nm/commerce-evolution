package dev.chaunm.commerceevolution.cart.domain.event;

import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartItemId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEvent;

public record CartItemQuantityChangedEvent(
        CartId cartId,
        CartItemId cartItemId,
        VariantId variantId,
        int newQuantity
) implements DomainEvent {
}
