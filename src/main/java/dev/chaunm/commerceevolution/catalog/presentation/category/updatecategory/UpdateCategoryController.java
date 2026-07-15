package dev.chaunm.commerceevolution.catalog.presentation.category.updatecategory;

import dev.chaunm.commerceevolution.catalog.application.usecase.category.updatecategory.UpdateCategoryMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.updatecategory.UpdateCategoryResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.updatecategory.UpdateCategoryUseCase;
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
@RequestMapping("/api/v1/catalog/categories/{id}")
@RequiredArgsConstructor
public class UpdateCategoryController {

    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final UpdateCategoryMapper mapper;

    @PutMapping
    public ResponseEntity<UpdateCategoryResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCategoryRequest request
    ) {
        UpdateCategoryResult result = updateCategoryUseCase.update(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
