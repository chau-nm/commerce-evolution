package dev.chaunm.commerceevolution.catalog.application.usecase.category.restorecategory;

import dev.chaunm.commerceevolution.catalog.presentation.category.restorecategory.RestoreCategoryResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestoreCategoryMapper {
    RestoreCategoryCommand toCommand(UUID id);
    RestoreCategoryResponse toResponse(RestoreCategoryResult result);
}
