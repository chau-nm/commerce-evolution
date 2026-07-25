package dev.chaunm.commerceevolution.catalog.presentation.variant.disablevariant;

import dev.chaunm.commerceevolution.catalog.application.usecase.variant.disablevariant.DisableVariantMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.disablevariant.DisableVariantResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.disablevariant.DisableVariantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variants/{variantId}/disable")
@RequiredArgsConstructor
public class DisableVariantController {

    private final DisableVariantUseCase disableVariantUseCase;
    private final DisableVariantMapper mapper;

    @PostMapping
    public ResponseEntity<DisableVariantResponse> disable(
            @PathVariable UUID productId,
            @PathVariable UUID variantId
    ) {
        DisableVariantResult result = disableVariantUseCase.disable(mapper.toCommand(productId, variantId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
