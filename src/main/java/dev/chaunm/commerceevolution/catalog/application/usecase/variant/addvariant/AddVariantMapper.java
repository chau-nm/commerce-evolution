package dev.chaunm.commerceevolution.catalog.application.usecase.variant.addvariant;

import dev.chaunm.commerceevolution.catalog.presentation.variant.addvariant.AddVariantRequest;
import dev.chaunm.commerceevolution.catalog.presentation.variant.addvariant.AddVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AddVariantMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "sku", source = "request.sku")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "price", source = "request.price")
    AddVariantCommand toCommand(UUID productId, AddVariantRequest request);

    AddVariantResponse toResponse(AddVariantResult result);
}
