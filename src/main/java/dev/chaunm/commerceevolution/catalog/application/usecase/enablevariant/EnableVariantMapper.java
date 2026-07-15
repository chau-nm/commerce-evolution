package dev.chaunm.commerceevolution.catalog.application.usecase.enablevariant;

import dev.chaunm.commerceevolution.catalog.presentation.enablevariant.EnableVariantResponse;
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
