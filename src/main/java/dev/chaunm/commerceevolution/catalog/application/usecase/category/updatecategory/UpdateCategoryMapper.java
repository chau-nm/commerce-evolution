package dev.chaunm.commerceevolution.catalog.application.usecase.category.updatecategory;

import dev.chaunm.commerceevolution.catalog.presentation.category.updatecategory.UpdateCategoryRequest;
import dev.chaunm.commerceevolution.catalog.presentation.category.updatecategory.UpdateCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateCategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "request.name")
    UpdateCategoryCommand toCommand(UUID id, UpdateCategoryRequest request);

    UpdateCategoryResponse toResponse(UpdateCategoryResult result);
}
