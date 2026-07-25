package dev.chaunm.commerceevolution.order.infrastructure.client;

import dev.chaunm.commerceevolution.order.application.port.PaymentServiceClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

/**
 * A 409 from payment-service means a payment was already initiated for this order (its own
 * existsByOrderId guard) — that is a successful, idempotent outcome of a retried call, not a
 * failure, so it must never be retried further or trip the circuit breaker.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentServiceClientImpl implements PaymentServiceClient {

    private final RestClient paymentServiceRestClient;
    private final CircuitBreaker paymentServiceCircuitBreaker;
    private final Retry paymentServiceRetry;

    @Value("${app.internal.api-key}")
    private String internalApiKey;

    @Override
    public void initiatePayment(UUID orderId, long amount) {
        Runnable call = () -> paymentServiceRestClient.post()
                .uri("/api/v1/payments")
                .header("X-Internal-Api-Key", internalApiKey)
                .header("Idempotency-Key", orderId.toString())
                .body(new InitiatePaymentWebhookRequest(orderId, amount))
                .retrieve()
                .toBodilessEntity();

        Runnable resilientCall = Retry.decorateRunnable(
                paymentServiceRetry,
                CircuitBreaker.decorateRunnable(paymentServiceCircuitBreaker, call));

        try {
            resilientCall.run();
        } catch (HttpClientErrorException.Conflict e) {
            log.info("payment-service already has a payment for order {}; treating as delivered", orderId);
        } catch (Exception e) {
            log.error("Failed to initiate payment for order {}: {}", orderId, e.getMessage());
        }
    }

    private record InitiatePaymentWebhookRequest(UUID orderId, long amount) {
    }
}
