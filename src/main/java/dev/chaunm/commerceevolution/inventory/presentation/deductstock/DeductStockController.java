package dev.chaunm.commerceevolution.inventory.presentation.deductstock;

import dev.chaunm.commerceevolution.inventory.application.usecase.deductstock.DeductStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.deductstock.DeductStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.deductstock.DeductStockUseCase;
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
@RequestMapping("/api/v1/inventory/inventories/{variantId}/deduct")
@RequiredArgsConstructor
public class DeductStockController {

    private final DeductStockUseCase deductStockUseCase;
    private final DeductStockMapper mapper;

    @PostMapping
    public ResponseEntity<DeductStockResponse> deduct(
            @PathVariable UUID variantId,
            @Valid @RequestBody DeductStockRequest request
    ) {
        DeductStockResult result = deductStockUseCase.deduct(mapper.toCommand(variantId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
