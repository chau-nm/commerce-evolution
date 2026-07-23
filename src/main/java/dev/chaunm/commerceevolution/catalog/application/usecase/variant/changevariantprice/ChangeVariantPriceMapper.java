package dev.chaunm.commerceevolution.catalog.application.usecase.variant.changevariantprice;

import dev.chaunm.commerceevolution.catalog.presentation.variant.changevariantprice.ChangeVariantPriceRequest;
import dev.chaunm.commerceevolution.catalog.presentation.variant.changevariantprice.ChangeVariantPriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ChangeVariantPriceMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "variantId", source = "variantId")
    @Mapping(target = "price", source = "request.price")
    ChangeVariantPriceCommand toCommand(UUID productId, UUID variantId, ChangeVariantPriceRequest request);

    ChangeVariantPriceResponse toResponse(ChangeVariantPriceResult result);
}
