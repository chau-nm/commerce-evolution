package dev.chaunm.commerceevolution.cart.domain.factory;

import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;

import java.util.List;

public class CartFactory {

    public static Cart create(CustomerId customerId) {
        return new Cart(CartId.generate(), customerId, List.of(), null);
    }
}
