package dev.chaunm.commerceevolution.paymentevents.presentation;

import dev.chaunm.commerceevolution.paymentevents.application.usecase.handlepaymentevent.HandlePaymentEventCommand;
import dev.chaunm.commerceevolution.paymentevents.application.usecase.handlepaymentevent.HandlePaymentEventUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inbound webhook called by payment-service once a payment settles. Reachable only with the
 * internal API key — see {@code InternalApiKeyFilter} — since this is a service-to-service call.
 */
@RestController
@RequestMapping("/internal/payment-events")
@RequiredArgsConstructor
public class PaymentEventWebhookController {

    private final HandlePaymentEventUseCase handlePaymentEventUseCase;

    @PostMapping
    public ResponseEntity<Void> handle(@Valid @RequestBody PaymentEventWebhookRequest request) {
        handlePaymentEventUseCase.handle(new HandlePaymentEventCommand(
                request.paymentId(), request.orderId(), request.status()));
        return ResponseEntity.noContent().build();
    }
}
