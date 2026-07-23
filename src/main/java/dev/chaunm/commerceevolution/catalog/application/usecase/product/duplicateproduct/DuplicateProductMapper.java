package dev.chaunm.commerceevolution.catalog.application.usecase.product.duplicateproduct;

import dev.chaunm.commerceevolution.catalog.presentation.product.duplicateproduct.DuplicateProductResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DuplicateProductMapper {
    DuplicateProductCommand toCommand(UUID id);
    DuplicateProductResponse toResponse(DuplicateProductResult result);
}
