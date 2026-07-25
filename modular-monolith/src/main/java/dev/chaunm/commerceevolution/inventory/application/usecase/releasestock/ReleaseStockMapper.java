package dev.chaunm.commerceevolution.inventory.application.usecase.releasestock;

import dev.chaunm.commerceevolution.inventory.presentation.releasestock.ReleaseStockRequest;
import dev.chaunm.commerceevolution.inventory.presentation.releasestock.ReleaseStockResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReleaseStockMapper {

    @Mapping(target = "variantId", source = "variantId")
    @Mapping(target = "quantity", source = "request.quantity")
    ReleaseStockCommand toCommand(UUID variantId, ReleaseStockRequest request);

    ReleaseStockResponse toResponse(ReleaseStockResult result);
}
