package dev.chaunm.commerceevolution.catalog.presentation.category.createcategory;

import dev.chaunm.commerceevolution.catalog.application.usecase.category.createcategory.CreateCategoryMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.createcategory.CreateCategoryResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.createcategory.CreateCategoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/categories")
@RequiredArgsConstructor
public class CreateCategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final CreateCategoryMapper mapper;

    @PostMapping
    public ResponseEntity<CreateCategoryResponse> create(
            @Valid @RequestBody CreateCategoryRequest request
    ) {
        CreateCategoryResult result = createCategoryUseCase.create(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }
}
