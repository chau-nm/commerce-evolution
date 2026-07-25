package dev.chaunm.commerceevolution.catalog.application.usecase.category.listcategories;

import dev.chaunm.commerceevolution.catalog.presentation.category.listcategories.CategorySummaryResponse;
import dev.chaunm.commerceevolution.catalog.presentation.category.listcategories.ListCategoriesRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListCategoriesMapper {
    ListCategoriesCommand toCommand(ListCategoriesRequest request);
    CategorySummaryResponse toResponse(CategorySummaryItem item);
}
