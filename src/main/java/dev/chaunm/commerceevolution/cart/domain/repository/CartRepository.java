package dev.chaunm.commerceevolution.cart.domain.repository;

import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;

import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findByCustomerId(CustomerId customerId);
}
