package dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantdetail;

import dev.chaunm.commerceevolution.catalog.presentation.variant.getvariantdetail.GetVariantDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetVariantDetailMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "variantId", source = "variantId")
    GetVariantDetailCommand toCommand(UUID productId, UUID variantId);

    GetVariantDetailResponse toResponse(GetVariantDetailResult result);
}
