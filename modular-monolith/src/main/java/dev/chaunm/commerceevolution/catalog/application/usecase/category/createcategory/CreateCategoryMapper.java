package dev.chaunm.commerceevolution.catalog.application.usecase.category.createcategory;

import dev.chaunm.commerceevolution.catalog.presentation.category.createcategory.CreateCategoryRequest;
import dev.chaunm.commerceevolution.catalog.presentation.category.createcategory.CreateCategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateCategoryMapper {
    CreateCategoryCommand toCommand(CreateCategoryRequest request);
    CreateCategoryResponse toResponse(CreateCategoryResult result);
}
