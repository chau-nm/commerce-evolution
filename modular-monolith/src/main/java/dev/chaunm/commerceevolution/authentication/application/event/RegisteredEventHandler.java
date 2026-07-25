package dev.chaunm.commerceevolution.authentication.application.event;

import dev.chaunm.commerceevolution.authentication.domain.event.AccountRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class RegisteredEventHandler {

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(AccountRegisteredEvent event) {
        log.info("Handling registered event for account: {}", event.accountId());
    }
}
