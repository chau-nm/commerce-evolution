package dev.chaunm.commerceevolution.catalog.application.usecase.createproduct;

import dev.chaunm.commerceevolution.catalog.presentation.createproduct.CreateProductRequest;
import dev.chaunm.commerceevolution.catalog.presentation.createproduct.CreateProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateProductMapper {
    CreateProductCommand toCommand(CreateProductRequest request);
    CreateProductResponse toResponse(CreateProductResult result);
}
