package dev.chaunm.commerceevolution.catalog.presentation.restoreproduct;

import dev.chaunm.commerceevolution.catalog.application.usecase.restoreproduct.RestoreProductMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.restoreproduct.RestoreProductResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.restoreproduct.RestoreProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{id}/restore")
@RequiredArgsConstructor
public class RestoreProductController {

    private final RestoreProductUseCase restoreProductUseCase;
    private final RestoreProductMapper mapper;

    @PostMapping
    public ResponseEntity<RestoreProductResponse> restore(@PathVariable UUID id) {
        RestoreProductResult result = restoreProductUseCase.restore(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
