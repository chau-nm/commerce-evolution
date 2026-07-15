package dev.chaunm.commerceevolution.catalog.application.usecase.brand.listbrands;

import dev.chaunm.commerceevolution.catalog.presentation.brand.listbrands.BrandSummaryResponse;
import dev.chaunm.commerceevolution.catalog.presentation.brand.listbrands.ListBrandsRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListBrandsMapper {
    ListBrandsCommand toCommand(ListBrandsRequest request);
    BrandSummaryResponse toResponse(BrandSummaryItem item);
}
