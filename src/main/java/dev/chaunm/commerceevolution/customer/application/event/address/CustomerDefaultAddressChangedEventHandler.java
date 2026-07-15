package dev.chaunm.commerceevolution.customer.application.event.address;

import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerDefaultAddressChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CustomerDefaultAddressChangedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CustomerDefaultAddressChangedEvent event) {
        log.info("Handling customer default address changed event for customer: {}, address: {}", event.customerId(), event.addressId());
    }
}
