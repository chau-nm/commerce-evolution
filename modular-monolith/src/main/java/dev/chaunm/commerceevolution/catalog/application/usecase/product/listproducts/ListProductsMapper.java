package dev.chaunm.commerceevolution.catalog.application.usecase.product.listproducts;

import dev.chaunm.commerceevolution.catalog.presentation.product.listproducts.ListProductsRequest;
import dev.chaunm.commerceevolution.catalog.presentation.product.listproducts.ProductionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListProductsMapper {
    ListProductsCommand toCommand(ListProductsRequest request);
    ProductionResponse toResponse(ProductSummaryItem item);
}
