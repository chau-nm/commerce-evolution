package dev.chaunm.commerceevolution.catalog.presentation.variant.updatevariant;

import dev.chaunm.commerceevolution.catalog.application.usecase.variant.updatevariant.UpdateVariantMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.updatevariant.UpdateVariantResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.updatevariant.UpdateVariantUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variants/{variantId}")
@RequiredArgsConstructor
public class UpdateVariantController {

    private final UpdateVariantUseCase updateVariantUseCase;
    private final UpdateVariantMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateVariantResponse> update(
            @PathVariable UUID productId,
            @PathVariable UUID variantId,
            @Valid @RequestBody UpdateVariantRequest request
    ) {
        UpdateVariantResult result = updateVariantUseCase.update(mapper.toCommand(productId, variantId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
