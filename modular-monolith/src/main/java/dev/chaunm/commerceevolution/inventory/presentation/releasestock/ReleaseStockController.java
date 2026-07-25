package dev.chaunm.commerceevolution.inventory.presentation.releasestock;

import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockMapper;
import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockResult;
import dev.chaunm.commerceevolution.inventory.application.usecase.releasestock.ReleaseStockUseCase;
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
@RequestMapping("/api/v1/inventory/inventories/{variantId}/release")
@RequiredArgsConstructor
public class ReleaseStockController {

    private final ReleaseStockUseCase releaseStockUseCase;
    private final ReleaseStockMapper mapper;

    @PostMapping
    public ResponseEntity<ReleaseStockResponse> release(
            @PathVariable UUID variantId,
            @Valid @RequestBody ReleaseStockRequest request
    ) {
        ReleaseStockResult result = releaseStockUseCase.release(mapper.toCommand(variantId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
