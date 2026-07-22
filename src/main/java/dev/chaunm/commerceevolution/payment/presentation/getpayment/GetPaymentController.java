package dev.chaunm.commerceevolution.payment.presentation.getpayment;

import dev.chaunm.commerceevolution.payment.application.usecase.getpayment.GetPaymentMapper;
import dev.chaunm.commerceevolution.payment.application.usecase.getpayment.GetPaymentResult;
import dev.chaunm.commerceevolution.payment.application.usecase.getpayment.GetPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/payment")
@RequiredArgsConstructor
public class GetPaymentController {

    private final GetPaymentUseCase getPaymentUseCase;
    private final GetPaymentMapper mapper;

    @GetMapping
    public ResponseEntity<GetPaymentResponse> getPayment(@PathVariable UUID orderId) {
        GetPaymentResult result = getPaymentUseCase.getByOrderId(mapper.toCommand(orderId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
