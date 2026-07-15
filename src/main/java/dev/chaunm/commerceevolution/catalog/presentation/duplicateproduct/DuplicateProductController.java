package dev.chaunm.commerceevolution.catalog.presentation.duplicateproduct;

import dev.chaunm.commerceevolution.catalog.application.usecase.duplicateproduct.DuplicateProductMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.duplicateproduct.DuplicateProductResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.duplicateproduct.DuplicateProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{id}/duplicate")
@RequiredArgsConstructor
public class DuplicateProductController {

    private final DuplicateProductUseCase duplicateProductUseCase;
    private final DuplicateProductMapper mapper;

    @PostMapping
    public ResponseEntity<DuplicateProductResponse> duplicate(@PathVariable UUID id) {
        DuplicateProductResult result = duplicateProductUseCase.duplicate(mapper.toCommand(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
