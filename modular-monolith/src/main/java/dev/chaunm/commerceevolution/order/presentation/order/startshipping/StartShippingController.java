package dev.chaunm.commerceevolution.order.presentation.order.startshipping;

import dev.chaunm.commerceevolution.order.application.usecase.startshipping.StartShippingMapper;
import dev.chaunm.commerceevolution.order.application.usecase.startshipping.StartShippingResult;
import dev.chaunm.commerceevolution.order.application.usecase.startshipping.StartShippingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/shipping")
@RequiredArgsConstructor
public class StartShippingController {

    private final StartShippingUseCase startShippingUseCase;
    private final StartShippingMapper mapper;

    @PutMapping
    public ResponseEntity<StartShippingResponse> startShipping(@PathVariable UUID orderId) {
        StartShippingResult result = startShippingUseCase.startShipping(mapper.toCommand(orderId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
