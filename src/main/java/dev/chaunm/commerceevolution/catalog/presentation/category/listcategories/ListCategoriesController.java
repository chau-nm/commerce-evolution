package dev.chaunm.commerceevolution.catalog.presentation.category.listcategories;

import dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories.CategorySummaryItem;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories.ListCategoriesMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories.ListCategoriesUseCase;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/categories")
@RequiredArgsConstructor
public class ListCategoriesController {

    private final ListCategoriesUseCase listCategoriesUseCase;
    private final ListCategoriesMapper mapper;

    @GetMapping
    public ResponseEntity<PaginationResponse<CategorySummaryResponse>> list(
            @ModelAttribute ListCategoriesRequest request
    ) {
        PaginationResult<CategorySummaryItem> result = listCategoriesUseCase.list(mapper.toCommand(request));

        return ResponseEntity.ok(result.map(mapper::toResponse).toResponse());
    }
}
