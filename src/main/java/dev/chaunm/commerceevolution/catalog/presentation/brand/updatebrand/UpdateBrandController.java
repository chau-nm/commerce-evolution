package dev.chaunm.commerceevolution.catalog.presentation.brand.updatebrand;

import dev.chaunm.commerceevolution.catalog.application.usecase.brand.updatebrand.UpdateBrandMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.updatebrand.UpdateBrandResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.updatebrand.UpdateBrandUseCase;
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
@RequestMapping("/api/v1/catalog/brands/{id}")
@RequiredArgsConstructor
public class UpdateBrandController {

    private final UpdateBrandUseCase updateBrandUseCase;
    private final UpdateBrandMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateBrandResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBrandRequest request
    ) {
        UpdateBrandResult result = updateBrandUseCase.update(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
