package dev.chaunm.commerceevolution.catalog.application.usecase.category.movecategory;

import dev.chaunm.commerceevolution.catalog.presentation.category.movecategory.MoveCategoryRequest;
import dev.chaunm.commerceevolution.catalog.presentation.category.movecategory.MoveCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MoveCategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "parentId", source = "request.parentId")
    MoveCategoryCommand toCommand(UUID id, MoveCategoryRequest request);

    MoveCategoryResponse toResponse(MoveCategoryResult result);
}
