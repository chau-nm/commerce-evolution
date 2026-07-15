package dev.chaunm.commerceevolution.catalog.presentation.deleteproduct;

import dev.chaunm.commerceevolution.catalog.application.usecase.deleteproduct.DeleteProductMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.deleteproduct.DeleteProductResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.deleteproduct.DeleteProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{id}")
@RequiredArgsConstructor
public class DeleteProductController {

    private final DeleteProductUseCase deleteProductUseCase;
    private final DeleteProductMapper mapper;

    @DeleteMapping
    public ResponseEntity<DeleteProductResponse> delete(@PathVariable UUID id) {
        DeleteProductResult result = deleteProductUseCase.delete(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
