package dev.chaunm.commerceevolution.catalog.application.usecase.updateproduct;

import dev.chaunm.commerceevolution.catalog.presentation.updateproduct.UpdateProductRequest;
import dev.chaunm.commerceevolution.catalog.presentation.updateproduct.UpdateProductResponse;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "slug", source = "request.slug")
    UpdateProductCommand toCommand(UUID id, UpdateProductRequest request);

    UpdateProductResponse toResponse(UpdateProductResult result);
}
