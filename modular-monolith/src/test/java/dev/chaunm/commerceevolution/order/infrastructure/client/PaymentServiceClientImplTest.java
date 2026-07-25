package dev.chaunm.commerceevolution.order.infrastructure.client;

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
 * Verifies the two properties this adapter must uphold per the migration's resilience
 * requirement: a successful call goes through cleanly, and a 409 (payment already initiated —
 * payment-service's own idempotency guard) is treated as a delivered, non-retried outcome
 * rather than an error propagated back into order placement.
 */
class PaymentServiceClientImplTest {

    private RestClient.Builder restClientBuilder;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        restClientBuilder = RestClient.builder().baseUrl("http://payment-service.test");
        mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
    }

    private PaymentServiceClientImpl clientUnderTest() {
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

        PaymentServiceClientImpl client = new PaymentServiceClientImpl(restClientBuilder.build(), circuitBreaker, retry);
        setInternalApiKey(client, "test-key");
        return client;
    }

    private void setInternalApiKey(PaymentServiceClientImpl client, String key) {
        try {
            var field = PaymentServiceClientImpl.class.getDeclaredField("internalApiKey");
            field.setAccessible(true);
            field.set(client, key);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void succeedsOnA2xxResponse() {
        UUID orderId = UUID.randomUUID();
        mockServer.expect(requestTo("http://payment-service.test/api/v1/payments"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andExpect(header("X-Internal-Api-Key", "test-key"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        assertThatCode(() -> clientUnderTest().initiatePayment(orderId, 100_000))
                .doesNotThrowAnyException();

        mockServer.verify();
    }

    @Test
    void treatsA409AsAnAlreadyDeliveredOutcomeRatherThanAnError() {
        UUID orderId = UUID.randomUUID();
        mockServer.expect(requestTo("http://payment-service.test/api/v1/payments"))
                .andRespond(withStatus(HttpStatus.CONFLICT));

        assertThatCode(() -> clientUnderTest().initiatePayment(orderId, 100_000))
                .doesNotThrowAnyException();

        // exactly one request was made — a 409 must not be retried
        mockServer.verify();
    }
}
