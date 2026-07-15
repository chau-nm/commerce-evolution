package dev.chaunm.commerceevolution.catalog.presentation.generatesku;

import dev.chaunm.commerceevolution.catalog.application.usecase.generatesku.GenerateSkuMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.generatesku.GenerateSkuResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.generatesku.GenerateSkuUseCase;
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
@RequestMapping("/api/v1/catalog/products/{productId}/variants/generate-sku")
@RequiredArgsConstructor
public class GenerateSkuController {

    private final GenerateSkuUseCase generateSkuUseCase;
    private final GenerateSkuMapper mapper;

    @PostMapping
    public ResponseEntity<GenerateSkuResponse> generate(
            @PathVariable UUID productId,
            @Valid @RequestBody GenerateSkuRequest request
    ) {
        GenerateSkuResult result = generateSkuUseCase.generate(mapper.toCommand(productId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
