package dev.chaunm.paymentservice.payment.infrastructure.client;

import dev.chaunm.paymentservice.payment.domain.model.valueobject.OrderId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.paymentservice.payment.domain.model.valueobject.PaymentStatus;
import dev.chaunm.paymentservice.shared.infrastructure.security.InternalApiKeyProperties;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Verifies the two properties this adapter must uphold: a successful delivery to order-service
 * goes through cleanly, and a 409 (order-service already recorded this outcome — its own
 * status-transition idempotency guard) is treated as delivered rather than an error, and is not
 * retried.
 */
class PaymentEventNotifierImplTest {

    private RestClient.Builder restClientBuilder;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        restClientBuilder = RestClient.builder().baseUrl("http://order-service.test");
        mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
    }

    private PaymentEventNotifierImpl notifierUnderTest() {
        CircuitBreaker circuitBreaker = CircuitBreaker.of("test", CircuitBreakerConfig.custom()
                .recordException(t -> !(t instanceof org.springframework.web.client.HttpClientErrorException))
                .build());
        Retry retry = Retry.of("test", RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(1))
                .retryExceptions(IOException.class, TimeoutException.class,
                        org.springframework.web.client.ResourceAccessException.class,
                        org.springframework.web.client.HttpServerErrorException.class)
                .build());

        return new PaymentEventNotifierImpl(
                restClientBuilder.build(), circuitBreaker, retry, new InternalApiKeyProperties("test-key"));
    }

    @Test
    void succeedsOnA2xxResponse() {
        mockServer.expect(requestTo("http://order-service.test/internal/payment-events"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andExpect(header("X-Internal-Api-Key", "test-key"))
                .andRespond(withSuccess());

        assertThatCode(() -> notifierUnderTest().notifyOutcome(
                PaymentId.generate(), new OrderId(UUID.randomUUID()), PaymentStatus.PAID))
                .doesNotThrowAnyException();

        mockServer.verify();
    }

    @Test
    void treatsA409AsAnAlreadyDeliveredOutcomeRatherThanAnError() {
        mockServer.expect(requestTo("http://order-service.test/internal/payment-events"))
                .andRespond(withStatus(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON));

        assertThatCode(() -> notifierUnderTest().notifyOutcome(
                PaymentId.generate(), new OrderId(UUID.randomUUID()), PaymentStatus.FAILED))
                .doesNotThrowAnyException();

        // exactly one request was made — a 409 must not be retried
        mockServer.verify();
    }
}
