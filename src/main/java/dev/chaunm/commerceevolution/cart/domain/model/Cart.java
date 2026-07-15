package dev.chaunm.commerceevolution.cart.domain.model;

import dev.chaunm.commerceevolution.cart.domain.event.CartClearedEvent;
import dev.chaunm.commerceevolution.cart.domain.event.CartItemAddedEvent;
import dev.chaunm.commerceevolution.cart.domain.event.CartItemQuantityChangedEvent;
import dev.chaunm.commerceevolution.cart.domain.event.CartItemRemovedEvent;
import dev.chaunm.commerceevolution.cart.domain.exception.CartItemNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartItemId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Cart extends AggregateRoot {

    @Getter
    private final CartId id;
    @Getter
    private final CustomerId customerId;
    private final List<CartItem> items;
    @Getter
    private Instant updatedAt;

    public Cart(
            CartId id,
            CustomerId customerId,
            List<CartItem> items,
            Instant updatedAt
    ) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.updatedAt = updatedAt;
    }

    public CartItem addItem(VariantId variantId, int quantity) {
        Quantity quantityToAdd = new Quantity(quantity);

        Optional<CartItem> existingItem = findByVariant(variantId);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.increaseQuantity(quantityToAdd);
            registerEvent(new CartItemQuantityChangedEvent(this.id, item.getId(), variantId, item.getQuantity().value()));
            return item;
        }

        CartItem item = new CartItem(CartItemId.generate(), variantId, quantityToAdd);
        items.add(item);
        registerEvent(new CartItemAddedEvent(this.id, item.getId(), variantId, item.getQuantity().value()));

        return item;
    }

    public void changeQuantity(CartItemId itemId, int newQuantity) {
        CartItem item = findItem(itemId);

        if (newQuantity <= 0) {
            items.remove(item);
            registerEvent(new CartItemRemovedEvent(this.id, itemId, item.getVariantId()));
            return;
        }

        item.changeQuantity(new Quantity(newQuantity));
        registerEvent(new CartItemQuantityChangedEvent(this.id, itemId, item.getVariantId(), newQuantity));
    }

    public void removeItem(CartItemId itemId) {
        CartItem item = findItem(itemId);

        items.remove(item);
        registerEvent(new CartItemRemovedEvent(this.id, itemId, item.getVariantId()));
    }

    public void clear() {
        items.clear();
        registerEvent(new CartClearedEvent(this.id));
    }

    private CartItem findItem(CartItemId itemId) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(CartItemNotFoundException::new);
    }

    private Optional<CartItem> findByVariant(VariantId variantId) {
        return items.stream()
                .filter(item -> item.getVariantId().equals(variantId))
                .findFirst();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
