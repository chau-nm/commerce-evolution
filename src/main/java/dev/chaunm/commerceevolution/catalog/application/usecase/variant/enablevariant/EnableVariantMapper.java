package dev.chaunm.commerceevolution.catalog.application.usecase.variant.enablevariant;

import dev.chaunm.commerceevolution.catalog.presentation.variant.enablevariant.EnableVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EnableVariantMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "variantId", source = "variantId")
    EnableVariantCommand toCommand(UUID productId, UUID variantId);

    EnableVariantResponse toResponse(EnableVariantResult result);
}
