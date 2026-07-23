package dev.chaunm.commerceevolution.payment.presentation.confirmpayment;

import dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment.ConfirmPaymentMapper;
import dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment.ConfirmPaymentResult;
import dev.chaunm.commerceevolution.payment.application.usecase.confirmpayment.ConfirmPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Stands in for a gateway webhook: in a real integration this would be called by the payment
 * processor, not the client. Left under /api/v1/payments so it's easy to trigger manually
 * until a real webhook receiver exists.
 */
@RestController
@RequestMapping("/api/v1/payments/{paymentId}/confirm")
@RequiredArgsConstructor
public class ConfirmPaymentController {

    private final ConfirmPaymentUseCase confirmPaymentUseCase;
    private final ConfirmPaymentMapper mapper;

    @PutMapping
    public ResponseEntity<ConfirmPaymentResponse> confirm(@PathVariable UUID paymentId) {
        ConfirmPaymentResult result = confirmPaymentUseCase.confirm(mapper.toCommand(paymentId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
