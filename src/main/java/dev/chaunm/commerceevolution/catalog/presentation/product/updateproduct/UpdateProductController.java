package dev.chaunm.commerceevolution.catalog.presentation.product.updateproduct;

import dev.chaunm.commerceevolution.catalog.application.usecase.product.updateproduct.UpdateProductMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.updateproduct.UpdateProductResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.updateproduct.UpdateProductUseCase;
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
@RequestMapping("/api/v1/catalog/products/{id}")
@RequiredArgsConstructor
public class UpdateProductController {

    private final UpdateProductUseCase updateProductUseCase;
    private final UpdateProductMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        UpdateProductResult result = updateProductUseCase.update(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
