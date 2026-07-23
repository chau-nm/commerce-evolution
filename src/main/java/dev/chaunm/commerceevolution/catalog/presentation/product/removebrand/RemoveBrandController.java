package dev.chaunm.commerceevolution.catalog.presentation.product.removebrand;

import dev.chaunm.commerceevolution.catalog.application.usecase.product.removebrand.RemoveBrandMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.removebrand.RemoveBrandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/brand")
@RequiredArgsConstructor
public class RemoveBrandController {

    private final RemoveBrandUseCase removeBrandUseCase;
    private final RemoveBrandMapper mapper;

    @DeleteMapping
    public ResponseEntity<Void> removeBrand(@PathVariable UUID productId) {
        removeBrandUseCase.removeBrand(mapper.toCommand(productId));
        return ResponseEntity.noContent().build();
    }
}
