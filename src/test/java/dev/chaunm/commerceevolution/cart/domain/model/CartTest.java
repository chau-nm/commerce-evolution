package dev.chaunm.commerceevolution.cart.domain.model;

import dev.chaunm.commerceevolution.cart.domain.event.CartClearedEvent;
import dev.chaunm.commerceevolution.cart.domain.event.CartItemAddedEvent;
import dev.chaunm.commerceevolution.cart.domain.event.CartItemQuantityChangedEvent;
import dev.chaunm.commerceevolution.cart.domain.event.CartItemRemovedEvent;
import dev.chaunm.commerceevolution.cart.domain.exception.CartItemNotFoundException;
import dev.chaunm.commerceevolution.cart.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.cart.domain.factory.CartFactory;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartItemId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.VariantId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = CartFactory.create(new CustomerId(UUID.randomUUID()));
        cart.clearDomainEvents();
    }

    @Test
    void addItemAppendsNewItemAndRegistersEvent() {
        VariantId variantId = new VariantId(UUID.randomUUID());

        CartItem item = cart.addItem(variantId, 2);

        assertThat(cart.getItems()).containsExactly(item);
        assertThat(item.getVariantId()).isEqualTo(variantId);
        assertThat(item.getQuantity().value()).isEqualTo(2);
        assertThat(cart.domainEvents())
                .singleElement()
                .isInstanceOf(CartItemAddedEvent.class);
    }

    @Test
    void addingTheSameVariantTwiceIncreasesQuantityInsteadOfCreatingANewItem() {
        VariantId variantId = new VariantId(UUID.randomUUID());
        cart.addItem(variantId, 2);
        cart.clearDomainEvents();

        CartItem item = cart.addItem(variantId, 3);

        assertThat(cart.getItems()).hasSize(1);
        assertThat(item.getQuantity().value()).isEqualTo(5);
        assertThat(cart.domainEvents())
                .singleElement()
                .isInstanceOf(CartItemQuantityChangedEvent.class);
    }

    @Test
    void addItemRejectsNonPositiveQuantity() {
        VariantId variantId = new VariantId(UUID.randomUUID());

        assertThatThrownBy(() -> cart.addItem(variantId, 0))
                .isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void changeQuantityUpdatesQuantityAndRegistersEvent() {
        CartItem item = cart.addItem(new VariantId(UUID.randomUUID()), 2);
        cart.clearDomainEvents();

        cart.changeQuantity(item.getId(), 5);

        assertThat(item.getQuantity().value()).isEqualTo(5);
        assertThat(cart.domainEvents())
                .singleElement()
                .isInstanceOf(CartItemQuantityChangedEvent.class);
    }

    @Test
    void changingQuantityToZeroRemovesTheItemAutomatically() {
        CartItem item = cart.addItem(new VariantId(UUID.randomUUID()), 2);
        cart.clearDomainEvents();

        cart.changeQuantity(item.getId(), 0);

        assertThat(cart.getItems()).doesNotContain(item);
        assertThat(cart.domainEvents())
                .singleElement()
                .isInstanceOf(CartItemRemovedEvent.class);
    }

    @Test
    void changeQuantityThrowsWhenItemDoesNotExist() {
        assertThatThrownBy(() -> cart.changeQuantity(CartItemId.generate(), 1))
                .isInstanceOf(CartItemNotFoundException.class);
    }

    @Test
    void removeItemRemovesItAndRegistersEvent() {
        CartItem item = cart.addItem(new VariantId(UUID.randomUUID()), 2);
        cart.clearDomainEvents();

        cart.removeItem(item.getId());

        assertThat(cart.getItems()).doesNotContain(item);
        assertThat(cart.domainEvents())
                .singleElement()
                .isInstanceOf(CartItemRemovedEvent.class);
    }

    @Test
    void removeItemThrowsWhenItemDoesNotExist() {
        assertThatThrownBy(() -> cart.removeItem(CartItemId.generate()))
                .isInstanceOf(CartItemNotFoundException.class);
    }

    @Test
    void clearRemovesAllItemsAndRegistersEvent() {
        cart.addItem(new VariantId(UUID.randomUUID()), 1);
        cart.addItem(new VariantId(UUID.randomUUID()), 2);
        cart.clearDomainEvents();

        cart.clear();

        assertThat(cart.getItems()).isEmpty();
        assertThat(cart.domainEvents())
                .singleElement()
                .isInstanceOf(CartClearedEvent.class);
    }
}
