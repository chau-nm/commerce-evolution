package dev.chaunm.commerceevolution.catalog.presentation.product.publishproduct;

import dev.chaunm.commerceevolution.catalog.application.usecase.product.publishproduct.PublishProductMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.publishproduct.PublishProductResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.publishproduct.PublishProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products/{id}/publish")
@RequiredArgsConstructor
public class PublishProductController {

    private final PublishProductUseCase publishProductUseCase;
    private final PublishProductMapper mapper;

    @PostMapping
    public ResponseEntity<PublishProductResponse> publish(@PathVariable UUID id) {
        PublishProductResult result = publishProductUseCase.publish(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
