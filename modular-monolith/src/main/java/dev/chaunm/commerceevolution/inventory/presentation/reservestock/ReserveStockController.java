package dev.chaunm.commerceevolution.inventory.presentation.reservestock;

import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory/inventories/{variantId}/reserve")
@RequiredArgsConstructor
public class ReserveStockController {

    private final ReserveStockUseCase reserveStockUseCase;
    private final ReserveStockMapper mapper;

    @PostMapping
    public ResponseEntity<ReserveStockResponse> reserve(
            @PathVariable UUID variantId,
            @Valid @RequestBody ReserveStockRequest request
    ) {
        ReserveStockResult result = reserveStockUseCase.reserve(mapper.toCommand(variantId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
