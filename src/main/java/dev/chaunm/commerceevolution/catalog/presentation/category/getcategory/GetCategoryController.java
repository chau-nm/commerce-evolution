package dev.chaunm.commerceevolution.catalog.presentation.category.getcategory;

import dev.chaunm.commerceevolution.catalog.application.usecase.category.getcategory.GetCategoryMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.getcategory.GetCategoryResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.getcategory.GetCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/categories/{id}")
@RequiredArgsConstructor
public class GetCategoryController {

    private final GetCategoryUseCase getCategoryUseCase;
    private final GetCategoryMapper mapper;

    @GetMapping
    public ResponseEntity<GetCategoryResponse> getCategory(@PathVariable UUID id) {
        GetCategoryResult result = getCategoryUseCase.getCategory(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
