package dev.chaunm.commerceevolution.catalog.application.usecase.removevariant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RemoveVariantMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "variantId", source = "variantId")
    RemoveVariantCommand toCommand(UUID productId, UUID variantId);
}
