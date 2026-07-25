package dev.chaunm.paymentservice.payment.presentation.initiatepayment;

import dev.chaunm.paymentservice.payment.application.usecase.initiatepayment.InitiatePaymentMapper;
import dev.chaunm.paymentservice.payment.application.usecase.initiatepayment.InitiatePaymentResult;
import dev.chaunm.paymentservice.payment.application.usecase.initiatepayment.InitiatePaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Called synchronously by order-service right after an order is placed. Reachable only with the
 * internal API key — see {@code InternalApiKeyFilter} — since this is a service-to-service call,
 * never an end-user one.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class InitiatePaymentController {

    private final InitiatePaymentUseCase initiatePaymentUseCase;
    private final InitiatePaymentMapper mapper;

    @PostMapping
    public ResponseEntity<InitiatePaymentResponse> initiate(@Valid @RequestBody InitiatePaymentRequest request) {
        InitiatePaymentResult result = initiatePaymentUseCase.initiate(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
