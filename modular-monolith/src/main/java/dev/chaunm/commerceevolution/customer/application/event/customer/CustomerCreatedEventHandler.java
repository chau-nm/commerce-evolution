package dev.chaunm.commerceevolution.customer.application.event.customer;

import dev.chaunm.commerceevolution.customer.domain.event.customer.CustomerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CustomerCreatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CustomerCreatedEvent event) {
        log.info("Handling customer created event for customer: {}, account: {}", event.customerId(), event.accountId());
    }
}
