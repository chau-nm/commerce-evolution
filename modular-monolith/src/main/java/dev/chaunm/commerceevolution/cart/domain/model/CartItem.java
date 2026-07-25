package dev.chaunm.commerceevolution.cart.domain.model;

import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartItemId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.VariantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItem {
    private final CartItemId id;
    private final VariantId variantId;
    private Quantity quantity;

    void increaseQuantity(Quantity delta) {
        this.quantity = this.quantity.add(delta);
    }

    void changeQuantity(Quantity newQuantity) {
        this.quantity = newQuantity;
    }
}
