package dev.chaunm.commerceevolution.catalog.application.event.product;

import dev.chaunm.commerceevolution.catalog.domain.event.product.ProductRestoredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ProductRestoredEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(ProductRestoredEvent event) {
        log.info("Handling product restored event for product: {}", event.productId());
    }
}
