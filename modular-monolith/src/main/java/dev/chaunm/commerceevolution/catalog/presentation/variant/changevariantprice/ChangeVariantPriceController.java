package dev.chaunm.commerceevolution.catalog.presentation.variant.changevariantprice;

import dev.chaunm.commerceevolution.catalog.application.usecase.variant.changevariantprice.ChangeVariantPriceMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.changevariantprice.ChangeVariantPriceResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.changevariantprice.ChangeVariantPriceUseCase;
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
@RequestMapping("/api/v1/catalog/products/{productId}/variants/{variantId}/price")
@RequiredArgsConstructor
public class ChangeVariantPriceController {

    private final ChangeVariantPriceUseCase changeVariantPriceUseCase;
    private final ChangeVariantPriceMapper mapper;

    @PostMapping
    public ResponseEntity<ChangeVariantPriceResponse> changePrice(
            @PathVariable UUID productId,
            @PathVariable UUID variantId,
            @Valid @RequestBody ChangeVariantPriceRequest request
    ) {
        ChangeVariantPriceResult result = changeVariantPriceUseCase.changePrice(mapper.toCommand(productId, variantId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
