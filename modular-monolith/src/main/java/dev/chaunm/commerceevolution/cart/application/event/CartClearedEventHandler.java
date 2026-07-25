package dev.chaunm.commerceevolution.cart.application.event;

import dev.chaunm.commerceevolution.cart.domain.event.CartClearedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CartClearedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CartClearedEvent event) {
        log.info("Handling cart cleared event for cart: {}", event.cartId());
    }
}
