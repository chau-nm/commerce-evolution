package dev.chaunm.commerceevolution.catalog.presentation.assigncategory;

import dev.chaunm.commerceevolution.catalog.application.usecase.assigncategory.AssignCategoryMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.assigncategory.AssignCategoryResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.assigncategory.AssignCategoryUseCase;
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
@RequestMapping("/api/v1/catalog/products/{productId}/category")
@RequiredArgsConstructor
public class AssignCategoryController {

    private final AssignCategoryUseCase assignCategoryUseCase;
    private final AssignCategoryMapper mapper;

    @PutMapping
    public ResponseEntity<AssignCategoryResponse> assignCategory(
            @PathVariable UUID productId,
            @Valid @RequestBody AssignCategoryRequest request
    ) {
        AssignCategoryResult result = assignCategoryUseCase.assignCategory(mapper.toCommand(productId, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
