package dev.chaunm.commerceevolution.customer.application.event.address;

import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CustomerAddressRemovedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CustomerAddressRemovedEvent event) {
        log.info("Handling customer address removed event for customer: {}, address: {}", event.customerId(), event.addressId());
    }
}
