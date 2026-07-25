package dev.chaunm.commerceevolution.catalog.presentation.brand.listbrands;

import dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands.BrandSummaryItem;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands.ListBrandsMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands.ListBrandsUseCase;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog/brands")
@RequiredArgsConstructor
public class ListBrandsController {

    private final ListBrandsUseCase listBrandsUseCase;
    private final ListBrandsMapper mapper;

    @GetMapping
    public ResponseEntity<PaginationResponse<BrandSummaryResponse>> list(
            @ModelAttribute ListBrandsRequest request
    ) {
        PaginationResult<BrandSummaryItem> result = listBrandsUseCase.list(mapper.toCommand(request));

        return ResponseEntity.ok(result.map(mapper::toResponse).toResponse());
    }
}
