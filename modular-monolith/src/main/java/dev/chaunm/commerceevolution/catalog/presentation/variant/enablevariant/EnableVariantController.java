package dev.chaunm.commerceevolution.catalog.presentation.variant.enablevariant;

import dev.chaunm.commerceevolution.catalog.application.usecase.variant.enablevariant.EnableVariantMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.enablevariant.EnableVariantResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.enablevariant.EnableVariantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variants/{variantId}/enable")
@RequiredArgsConstructor
public class EnableVariantController {

    private final EnableVariantUseCase enableVariantUseCase;
    private final EnableVariantMapper mapper;

    @PostMapping
    public ResponseEntity<EnableVariantResponse> enable(
            @PathVariable UUID productId,
            @PathVariable UUID variantId
    ) {
        EnableVariantResult result = enableVariantUseCase.enable(mapper.toCommand(productId, variantId));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
