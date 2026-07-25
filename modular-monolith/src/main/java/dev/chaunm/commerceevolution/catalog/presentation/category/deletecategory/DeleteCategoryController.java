package dev.chaunm.commerceevolution.catalog.presentation.category.deletecategory;

import dev.chaunm.commerceevolution.catalog.application.usecase.category.deletecategory.DeleteCategoryMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.deletecategory.DeleteCategoryResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.deletecategory.DeleteCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/categories/{id}")
@RequiredArgsConstructor
public class DeleteCategoryController {

    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final DeleteCategoryMapper mapper;

    @DeleteMapping
    public ResponseEntity<DeleteCategoryResponse> delete(@PathVariable UUID id) {
        DeleteCategoryResult result = deleteCategoryUseCase.delete(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
