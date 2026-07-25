package dev.chaunm.commerceevolution.customer.application.event.address;

import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressAddedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class CustomerAddressAddedEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(CustomerAddressAddedEvent event) {
        log.info("Handling customer address added event for customer: {}, address: {}", event.customerId(), event.addressId());
    }
}
