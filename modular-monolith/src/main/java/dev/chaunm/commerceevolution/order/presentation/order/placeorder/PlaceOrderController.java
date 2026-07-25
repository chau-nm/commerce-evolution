package dev.chaunm.commerceevolution.order.presentation.order.placeorder;

import dev.chaunm.commerceevolution.order.application.usecase.placeorder.PlaceOrderMapper;
import dev.chaunm.commerceevolution.order.application.usecase.placeorder.PlaceOrderResult;
import dev.chaunm.commerceevolution.order.application.usecase.placeorder.PlaceOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class PlaceOrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final PlaceOrderMapper mapper;

    @PostMapping
    public ResponseEntity<PlaceOrderResponse> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        PlaceOrderResult result = placeOrderUseCase.placeOrder(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
