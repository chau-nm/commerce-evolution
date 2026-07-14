package dev.chaunm.commerceevolution.catalog.application.usecase.assigncategory;

import dev.chaunm.commerceevolution.catalog.presentation.assigncategory.AssignCategoryRequest;
import dev.chaunm.commerceevolution.catalog.presentation.assigncategory.AssignCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AssignCategoryMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "categoryId", source = "request.categoryId")
    AssignCategoryCommand toCommand(UUID productId, AssignCategoryRequest request);

    AssignCategoryResponse toResponse(AssignCategoryResult result);
}
