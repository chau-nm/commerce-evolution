package dev.chaunm.commerceevolution.catalog.application.usecase.listproducts;

import dev.chaunm.commerceevolution.catalog.presentation.listproducts.ListProductsRequest;
import dev.chaunm.commerceevolution.catalog.presentation.listproducts.ProductionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListProductsMapper {
    ListProductsCommand toCommand(ListProductsRequest request);
    ProductionResponse toResponse(ProductSummaryItem item);
}
