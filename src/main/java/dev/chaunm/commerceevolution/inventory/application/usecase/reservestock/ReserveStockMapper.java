package dev.chaunm.commerceevolution.inventory.application.usecase.reservestock;

import dev.chaunm.commerceevolution.inventory.presentation.reservestock.ReserveStockRequest;
import dev.chaunm.commerceevolution.inventory.presentation.reservestock.ReserveStockResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReserveStockMapper {

    @Mapping(target = "variantId", source = "variantId")
    @Mapping(target = "quantity", source = "request.quantity")
    ReserveStockCommand toCommand(UUID variantId, ReserveStockRequest request);

    ReserveStockResponse toResponse(ReserveStockResult result);
}
