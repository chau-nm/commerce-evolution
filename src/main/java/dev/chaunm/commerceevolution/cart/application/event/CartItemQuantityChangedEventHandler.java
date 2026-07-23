package dev.chaunm.commerceevolution.cart.application.event;

import dev.chaunm.commerceevolution.cart.domain.event.CartItemQuantityChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CartItemQuantityChangedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CartItemQuantityChangedEvent event) {
        log.info("Handling cart item quantity changed event for cart: {}, variant: {}, newQuantity: {}",
                event.cartId(), event.variantId(), event.newQuantity());
    }
}
