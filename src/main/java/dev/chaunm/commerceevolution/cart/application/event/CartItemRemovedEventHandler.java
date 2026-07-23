package dev.chaunm.commerceevolution.cart.application.event;

import dev.chaunm.commerceevolution.cart.domain.event.CartItemRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CartItemRemovedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CartItemRemovedEvent event) {
        log.info("Handling cart item removed event for cart: {}, variant: {}", event.cartId(), event.variantId());
    }
}
