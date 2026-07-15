package dev.chaunm.commerceevolution.inventory.presentation.adjuststock;

import dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock.AdjustStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock.AdjustStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock.AdjustStockUseCase;
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
@RequestMapping("/api/v1/inventory/inventories/{variantId}/adjust")
@RequiredArgsConstructor
public class AdjustStockController {

    private final AdjustStockUseCase adjustStockUseCase;
    private final AdjustStockMapper mapper;

    @PostMapping
    public ResponseEntity<AdjustStockResponse> adjust(
            @PathVariable UUID variantId,
            @Valid @RequestBody AdjustStockRequest request
    ) {
        AdjustStockResult result = adjustStockUseCase.adjust(mapper.toCommand(variantId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
