package dev.chaunm.commerceevolution.catalog.application.usecase.product.assigncategory;

import dev.chaunm.commerceevolution.catalog.presentation.product.assigncategory.AssignCategoryRequest;
import dev.chaunm.commerceevolution.catalog.presentation.product.assigncategory.AssignCategoryResponse;
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
