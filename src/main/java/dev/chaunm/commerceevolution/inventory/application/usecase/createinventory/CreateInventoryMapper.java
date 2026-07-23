package dev.chaunm.commerceevolution.inventory.application.usecase.createinventory;

import dev.chaunm.commerceevolution.inventory.presentation.createinventory.CreateInventoryRequest;
import dev.chaunm.commerceevolution.inventory.presentation.createinventory.CreateInventoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateInventoryMapper {
    CreateInventoryCommand toCommand(CreateInventoryRequest request);
    CreateInventoryResponse toResponse(CreateInventoryResult result);
}
