package dev.chaunm.commerceevolution.catalog.presentation.variant.addvariant;

import dev.chaunm.commerceevolution.catalog.application.usecase.variant.addvariant.AddVariantMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.addvariant.AddVariantResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.addvariant.AddVariantUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variants")
@RequiredArgsConstructor
public class AddVariantController {

    private final AddVariantUseCase addVariantUseCase;
    private final AddVariantMapper mapper;

    @PostMapping
    public ResponseEntity<AddVariantResponse> addVariant(
            @PathVariable UUID productId,
            @Valid @RequestBody AddVariantRequest request
    ) {
        AddVariantResult result = addVariantUseCase.addVariant(mapper.toCommand(productId, request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
