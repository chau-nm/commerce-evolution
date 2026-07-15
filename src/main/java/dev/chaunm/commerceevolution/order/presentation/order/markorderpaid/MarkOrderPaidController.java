package dev.chaunm.commerceevolution.order.presentation.order.markorderpaid;

import dev.chaunm.commerceevolution.order.application.usecase.markorderpaid.MarkOrderPaidMapper;
import dev.chaunm.commerceevolution.order.application.usecase.markorderpaid.MarkOrderPaidResult;
import dev.chaunm.commerceevolution.order.application.usecase.markorderpaid.MarkOrderPaidUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/pay")
@RequiredArgsConstructor
public class MarkOrderPaidController {

    private final MarkOrderPaidUseCase markOrderPaidUseCase;
    private final MarkOrderPaidMapper mapper;

    @PutMapping
    public ResponseEntity<MarkOrderPaidResponse> markPaid(@PathVariable UUID orderId) {
        MarkOrderPaidResult result = markOrderPaidUseCase.markPaid(mapper.toCommand(orderId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
