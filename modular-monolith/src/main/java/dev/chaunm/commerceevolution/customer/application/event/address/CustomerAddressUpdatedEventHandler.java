package dev.chaunm.commerceevolution.customer.application.event.address;

import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CustomerAddressUpdatedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CustomerAddressUpdatedEvent event) {
        log.info("Handling customer address updated event for customer: {}, address: {}", event.customerId(), event.addressId());
    }
}
