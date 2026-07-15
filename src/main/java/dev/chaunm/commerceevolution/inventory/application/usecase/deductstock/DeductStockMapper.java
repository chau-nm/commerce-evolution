package dev.chaunm.commerceevolution.inventory.application.usecase.deductstock;

import dev.chaunm.commerceevolution.inventory.presentation.deductstock.DeductStockRequest;
import dev.chaunm.commerceevolution.inventory.presentation.deductstock.DeductStockResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DeductStockMapper {

    @Mapping(target = "variantId", source = "variantId")
    @Mapping(target = "quantity", source = "request.quantity")
    DeductStockCommand toCommand(UUID variantId, DeductStockRequest request);

    DeductStockResponse toResponse(DeductStockResult result);
}
