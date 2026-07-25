package dev.chaunm.commerceevolution.customer.application.event.customer;

import dev.chaunm.commerceevolution.customer.domain.event.customer.CustomerProfileUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CustomerProfileUpdatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CustomerProfileUpdatedEvent event) {
        log.info("Handling customer profile updated event for customer: {}", event.customerId());
    }
}
