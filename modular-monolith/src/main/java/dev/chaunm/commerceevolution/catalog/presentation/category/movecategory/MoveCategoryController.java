package dev.chaunm.commerceevolution.catalog.presentation.category.movecategory;

import dev.chaunm.commerceevolution.catalog.application.usecase.category.movecategory.MoveCategoryMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.movecategory.MoveCategoryResult;
import dev.chaunm.commerceevolution.catalog.application.usecase.category.movecategory.MoveCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/categories/{id}/move")
@RequiredArgsConstructor
public class MoveCategoryController {

    private final MoveCategoryUseCase moveCategoryUseCase;
    private final MoveCategoryMapper mapper;

    @PostMapping
    public ResponseEntity<MoveCategoryResponse> move(
            @PathVariable UUID id,
            @RequestBody MoveCategoryRequest request
    ) {
        MoveCategoryResult result = moveCategoryUseCase.move(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
