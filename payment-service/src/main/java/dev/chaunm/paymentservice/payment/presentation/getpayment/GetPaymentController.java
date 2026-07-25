package dev.chaunm.paymentservice.payment.presentation.getpayment;

import dev.chaunm.paymentservice.payment.application.usecase.getpayment.GetPaymentMapper;
import dev.chaunm.paymentservice.payment.application.usecase.getpayment.GetPaymentResult;
import dev.chaunm.paymentservice.payment.application.usecase.getpayment.GetPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Payment now owns this resource directly (it used to be nested under order-service's
 * /api/v1/orders/{orderId}/payment). Clients that used the old nested route must switch to this
 * one — see the migration report's "breaking changes" section.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class GetPaymentController {

    private final GetPaymentUseCase getPaymentUseCase;
    private final GetPaymentMapper mapper;

    @GetMapping("/{orderId}")
    public ResponseEntity<GetPaymentResponse> getPayment(@PathVariable UUID orderId) {
        GetPaymentResult result = getPaymentUseCase.getByOrderId(mapper.toCommand(orderId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
