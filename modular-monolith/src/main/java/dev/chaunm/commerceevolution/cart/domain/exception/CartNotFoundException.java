package dev.chaunm.commerceevolution.cart.domain.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;

public class CartNotFoundException extends NotFoundException {
    public CartNotFoundException() {
        super(CartErrorCode.CART_NOT_FOUND, "Cart not found");
    }
}
