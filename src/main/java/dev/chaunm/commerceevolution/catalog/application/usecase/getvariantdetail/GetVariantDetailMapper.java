package dev.chaunm.commerceevolution.catalog.application.usecase.getvariantdetail;

import dev.chaunm.commerceevolution.catalog.presentation.getvariantdetail.GetVariantDetailResponse;
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
