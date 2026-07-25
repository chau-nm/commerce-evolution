package dev.chaunm.commerceevolution.catalog.presentation.product.changebrand;

import dev.chaunm.commerceevolution.catalog.application.usecase.product.changebrand.ChangeBrandMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.changebrand.ChangeBrandResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.changebrand.ChangeBrandUseCase;
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
@RequestMapping("/api/v1/catalog/products/{productId}/brand")
@RequiredArgsConstructor
public class ChangeBrandController {

    private final ChangeBrandUseCase changeBrandUseCase;
    private final ChangeBrandMapper mapper;

    @PutMapping
    public ResponseEntity<ChangeBrandResponse> changeBrand(
            @PathVariable UUID productId,
            @Valid @RequestBody ChangeBrandRequest request
    ) {
        ChangeBrandResult result = changeBrandUseCase.changeBrand(mapper.toCommand(productId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
