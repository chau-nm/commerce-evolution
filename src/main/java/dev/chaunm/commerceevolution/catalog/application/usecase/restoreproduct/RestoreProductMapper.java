package dev.chaunm.commerceevolution.catalog.application.usecase.restoreproduct;

import dev.chaunm.commerceevolution.catalog.presentation.restoreproduct.RestoreProductResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RestoreProductMapper {
    RestoreProductCommand toCommand(UUID id);
    RestoreProductResponse toResponse(RestoreProductResult result);
}
