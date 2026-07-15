package dev.chaunm.commerceevolution.catalog.presentation.product.listproducts;

import dev.chaunm.commerceevolution.catalog.application.usecase.product.listproducts.ListProductsMapper;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.listproducts.ListProductsUseCase;
import dev.chaunm.commerceevolution.catalog.application.usecase.product.listproducts.ProductSummaryItem;
import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;
import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog/products")
@RequiredArgsConstructor
public class ListProductsController {

    private final ListProductsUseCase listProductsUseCase;
    private final ListProductsMapper mapper;

    @GetMapping
    public ResponseEntity<PaginationResponse<ProductionResponse>> list(
            @ModelAttribute ListProductsRequest request
    ) {
        PaginationResult<ProductSummaryItem> result = listProductsUseCase.list(mapper.toCommand(request));

        return ResponseEntity.ok(result.map(mapper::toResponse).toResponse());
    }
}
