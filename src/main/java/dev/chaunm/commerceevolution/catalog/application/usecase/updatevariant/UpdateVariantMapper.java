package dev.chaunm.commerceevolution.catalog.application.usecase.updatevariant;

import dev.chaunm.commerceevolution.catalog.presentation.updatevariant.UpdateVariantRequest;
import dev.chaunm.commerceevolution.catalog.presentation.updatevariant.UpdateVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateVariantMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "variantId", source = "variantId")
    @Mapping(target = "sku", source = "request.sku")
    @Mapping(target = "name", source = "request.name")
    UpdateVariantCommand toCommand(UUID productId, UUID variantId, UpdateVariantRequest request);

    UpdateVariantResponse toResponse(UpdateVariantResult result);
}
