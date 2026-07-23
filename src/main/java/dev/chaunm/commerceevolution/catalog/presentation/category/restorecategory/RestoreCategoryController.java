package dev.chaunm.commerceevolution.catalog.presentation.category.restorecategory;

import dev.chaunm.commerceevolution.catalog.application.usecase.category.restorecategory.RestoreCategoryMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.restorecategory.RestoreCategoryResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.restorecategory.RestoreCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/categories/{id}/restore")
@RequiredArgsConstructor
public class RestoreCategoryController {

    private final RestoreCategoryUseCase restoreCategoryUseCase;
    private final RestoreCategoryMapper mapper;

    @PostMapping
    public ResponseEntity<RestoreCategoryResponse> restore(@PathVariable UUID id) {
        RestoreCategoryResult result = restoreCategoryUseCase.restore(mapper.toCommand(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
