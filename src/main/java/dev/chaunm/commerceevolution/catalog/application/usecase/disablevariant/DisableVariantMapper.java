package dev.chaunm.commerceevolution.catalog.application.usecase.disablevariant;

import dev.chaunm.commerceevolution.catalog.presentation.disablevariant.DisableVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DisableVariantMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "variantId", source = "variantId")
    DisableVariantCommand toCommand(UUID productId, UUID variantId);

    DisableVariantResponse toResponse(DisableVariantResult result);
}
