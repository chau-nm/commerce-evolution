package dev.chaunm.commerceevolution.inventory.application.usecase.adjuststock;

import dev.chaunm.commerceevolution.inventory.presentation.adjuststock.AdjustStockRequest;
import dev.chaunm.commerceevolution.inventory.presentation.adjuststock.AdjustStockResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AdjustStockMapper {

    @Mapping(target = "variantId", source = "variantId")
    @Mapping(target = "quantityDelta", source = "request.quantityDelta")
    AdjustStockCommand toCommand(UUID variantId, AdjustStockRequest request);

    AdjustStockResponse toResponse(AdjustStockResult result);
}
