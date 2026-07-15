package dev.chaunm.commerceevolution.catalog.application.usecase.deleteproduct;

import dev.chaunm.commerceevolution.catalog.presentation.deleteproduct.DeleteProductResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DeleteProductMapper {
    DeleteProductCommand toCommand(UUID id);
    DeleteProductResponse toResponse(DeleteProductResult result);
}
