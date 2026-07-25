package dev.chaunm.commerceevolution.order.presentation.order.completeorder;

import dev.chaunm.commerceevolution.order.application.usecase.completeorder.CompleteOrderMapper;
import dev.chaunm.commerceevolution.order.application.usecase.completeorder.CompleteOrderResult;
import dev.chaunm.commerceevolution.order.application.usecase.completeorder.CompleteOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/complete")
@RequiredArgsConstructor
public class CompleteOrderController {

    private final CompleteOrderUseCase completeOrderUseCase;
    private final CompleteOrderMapper mapper;

    @PutMapping
    public ResponseEntity<CompleteOrderResponse> completeOrder(@PathVariable UUID orderId) {
        CompleteOrderResult result = completeOrderUseCase.completeOrder(mapper.toCommand(orderId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
