package dev.chaunm.commerceevolution.catalog.application.usecase.category.getcategory;

import dev.chaunm.commerceevolution.catalog.presentation.category.getcategory.GetCategoryResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetCategoryMapper {
    GetCategoryCommand toCommand(UUID id);
    GetCategoryResponse toResponse(GetCategoryResult result);
}
