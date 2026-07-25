package dev.chaunm.commerceevolution.cart.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class CartItemNotFoundException extends NotFoundException {
    public CartItemNotFoundException() {
        super(CartErrorCode.CART_ITEM_NOT_FOUND, "Cart item not found");
    }
}
