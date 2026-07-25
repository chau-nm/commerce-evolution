package dev.chaunm.paymentservice.payment.infrastructure.client;

import dev.chaunm.paymentservice.payment.application.port.PaymentEventNotifier;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentStatus;
import dev.chaunm.paymentservice.shared.infrastructure.security.InternalApiKeyProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

/**
 * Adapter for {@link PaymentEventNotifier}: reports a settled payment's outcome to
 * order-service's {@code POST /internal/payment-events} webhook. Every call is wrapped with a
 * retry (for transient network/5xx failures) inside a circuit breaker (to stop hammering
 * order-service once it's clearly down), per requirement that every cross-service HTTP call
 * gets timeout + retry + circuit breaker.
 * <p>
 * A 409 from order-service means this exact outcome was already recorded (order-service's own
 * status-transition guard rejects a repeat "mark paid") — that is a successful, idempotent
 * no-op, not a failure, so it must never be retried or count against the circuit breaker.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventNotifierImpl implements PaymentEventNotifier {

    private final RestClient orderServiceRestClient;
    private final CircuitBreaker orderServiceCircuitBreaker;
    private final Retry orderServiceRetry;
    private final InternalApiKeyProperties internalApiKeyProperties;

    @Override
    public void notifyOutcome(PaymentId paymentId, OrderId orderId, PaymentStatus status) {
        Runnable call = () -> orderServiceRestClient.post()
                .uri("/internal/payment-events")
                .header("X-Internal-Api-Key", internalApiKeyProperties.apiKey())
                .header("Idempotency-Key", paymentId.value() + ":" + status.name())
                .body(new PaymentEventWebhookRequest(paymentId.value(), orderId.value(), status.name()))
                .retrieve()
                .toBodilessEntity();

        Runnable resilientCall = Retry.decorateRunnable(
                orderServiceRetry,
                CircuitBreaker.decorateRunnable(orderServiceCircuitBreaker, call));

        try {
            resilientCall.run();
        } catch (HttpClientErrorException.Conflict e) {
            log.info("order-service already recorded outcome {} for payment {}; treating as delivered", status, paymentId);
        } catch (Exception e) {
            log.error("Failed to notify order-service of payment {} outcome {}: {}", paymentId, status, e.getMessage());
        }
    }
}
