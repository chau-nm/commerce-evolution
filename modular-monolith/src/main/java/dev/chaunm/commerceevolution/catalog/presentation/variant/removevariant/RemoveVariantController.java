package dev.chaunm.commerceevolution.catalog.presentation.variant.removevariant;

import dev.chaunm.commerceevolution.catalog.application.usecase.variant.removevariant.RemoveVariantMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.removevariant.RemoveVariantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variants/{variantId}")
@RequiredArgsConstructor
public class RemoveVariantController {

    private final RemoveVariantUseCase removeVariantUseCase;
    private final RemoveVariantMapper mapper;

    @DeleteMapping
    public ResponseEntity<Void> removeVariant(
            @PathVariable UUID productId,
            @PathVariable UUID variantId
    ) {
        removeVariantUseCase.removeVariant(mapper.toCommand(productId, variantId));
        return ResponseEntity.noContent().build();
    }
}
