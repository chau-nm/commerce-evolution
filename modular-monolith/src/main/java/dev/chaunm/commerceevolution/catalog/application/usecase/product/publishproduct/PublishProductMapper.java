package dev.chaunm.commerceevolution.catalog.application.usecase.product.publishproduct;

import dev.chaunm.commerceevolution.catalog.presentation.product.publishproduct.PublishProductResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PublishProductMapper {
    PublishProductCommand toCommand(UUID id);
    PublishProductResponse toResponse(PublishProductResult result);
}
