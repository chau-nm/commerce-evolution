package dev.chaunm.commerceevolution.catalog.application.usecase.product.unassigncategory;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UnassignCategoryMapper {
    UnassignCategoryCommand toCommand(UUID productId);
}
