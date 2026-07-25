package dev.chaunm.commerceevolution.catalog.application.usecase.product.createproduct;

import dev.chaunm.commerceevolution.catalog.presentation.product.createproduct.CreateProductRequest;
import dev.chaunm.commerceevolution.catalog.presentation.product.createproduct.CreateProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateProductMapper {
    CreateProductCommand toCommand(CreateProductRequest request);
    CreateProductResponse toResponse(CreateProductResult result);
}
