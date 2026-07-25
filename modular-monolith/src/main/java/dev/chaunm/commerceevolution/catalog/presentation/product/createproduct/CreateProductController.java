package dev.chaunm.commerceevolution.catalog.presentation.product.createproduct;

import dev.chaunm.commerceevolution.catalog.application.usecase.product.createproduct.CreateProductMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.createproduct.CreateProductResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.createproduct.CreateProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/products")
@RequiredArgsConstructor
public class CreateProductController {

    private final CreateProductUseCase createProductUseCase;
    private final CreateProductMapper mapper;

    @PostMapping
    public ResponseEntity<CreateProductResponse> create(
            @Valid @RequestBody CreateProductRequest request
    ) {
        CreateProductResult result = createProductUseCase.create(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
