package dev.chaunm.commerceevolution.inventory.application.usecase.getinventory;

import dev.chaunm.commerceevolution.inventory.presentation.getinventory.GetInventoryResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GetInventoryMapper {
    GetInventoryCommand toCommand(UUID variantId);
    GetInventoryResponse toResponse(GetInventoryResult result);
}
