package dev.chaunm.commerceevolution.order.presentation.order.cancelorder;

import dev.chaunm.commerceevolution.order.application.usecase.cancelorder.CancelOrderMapper;
import dev.chaunm.commerceevolution.order.application.usecase.cancelorder.CancelOrderResult;
import dev.chaunm.commerceevolution.order.application.usecase.cancelorder.CancelOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/cancel")
@RequiredArgsConstructor
public class CancelOrderController {

    private final CancelOrderUseCase cancelOrderUseCase;
    private final CancelOrderMapper mapper;

    @PutMapping
    public ResponseEntity<CancelOrderResponse> cancelOrder(@PathVariable UUID orderId) {
        CancelOrderResult result = cancelOrderUseCase.cancelOrder(mapper.toCommand(orderId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
