package dev.chaunm.commerceevolution.catalog.application.usecase.product.restoreproduct;

import dev.chaunm.commerceevolution.catalog.presentation.product.restoreproduct.RestoreProductResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestoreProductMapper {
    RestoreProductCommand toCommand(UUID id);
    RestoreProductResponse toResponse(RestoreProductResult result);
}
