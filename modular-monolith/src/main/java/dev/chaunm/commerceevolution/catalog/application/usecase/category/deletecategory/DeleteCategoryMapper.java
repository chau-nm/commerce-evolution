package dev.chaunm.commerceevolution.catalog.application.usecase.category.deletecategory;

import dev.chaunm.commerceevolution.catalog.presentation.category.deletecategory.DeleteCategoryResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DeleteCategoryMapper {
    DeleteCategoryCommand toCommand(UUID id);
    DeleteCategoryResponse toResponse(DeleteCategoryResult result);
}
