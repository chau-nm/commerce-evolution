package dev.chaunm.commerceevolution.order.presentation.order.getmyorders;

import dev.chaunm.commerceevolution.order.application.usecase.getmyorders.GetMyOrdersMapper;
import dev.chaunm.commerceevolution.order.application.usecase.getmyorders.GetMyOrdersUseCase;
import dev.chaunm.commerceevolution.order.application.usecase.getmyorders.OrderSummaryItem;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class GetMyOrdersController {

    private final GetMyOrdersUseCase getMyOrdersUseCase;
    private final GetMyOrdersMapper mapper;

    @GetMapping
    public ResponseEntity<PaginationResponse<OrderSummaryResponse>> getMyOrders(
            @ModelAttribute GetMyOrdersRequest request
    ) {
        PaginationResult<OrderSummaryItem> result = getMyOrdersUseCase.getMyOrders(mapper.toCommand(request));

        return ResponseEntity.ok(result.map(mapper::toResponse).toResponse());
    }
}
