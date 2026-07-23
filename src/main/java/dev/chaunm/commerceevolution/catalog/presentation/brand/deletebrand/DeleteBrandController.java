package dev.chaunm.commerceevolution.catalog.presentation.brand.deletebrand;

import dev.chaunm.commerceevolution.catalog.application.usecase.brand.deletebrand.DeleteBrandMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.deletebrand.DeleteBrandResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.deletebrand.DeleteBrandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/brands/{id}")
@RequiredArgsConstructor
public class DeleteBrandController {

    private final DeleteBrandUseCase deleteBrandUseCase;
    private final DeleteBrandMapper mapper;

    @DeleteMapping
    public ResponseEntity<DeleteBrandResponse> delete(@PathVariable UUID id) {
        DeleteBrandResult result = deleteBrandUseCase.delete(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
