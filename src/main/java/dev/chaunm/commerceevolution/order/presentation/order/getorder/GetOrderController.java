package dev.chaunm.commerceevolution.order.presentation.order.getorder;

import dev.chaunm.commerceevolution.order.application.usecase.getorder.GetOrderMapper;
import dev.chaunm.commerceevolution.order.application.usecase.getorder.GetOrderResult;
import dev.chaunm.commerceevolution.order.application.usecase.getorder.GetOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/{orderId}")
@RequiredArgsConstructor
public class GetOrderController {

    private final GetOrderUseCase getOrderUseCase;
    private final GetOrderMapper mapper;

    @GetMapping
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable UUID orderId) {
        GetOrderResult result = getOrderUseCase.getOrder(mapper.toCommand(orderId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
