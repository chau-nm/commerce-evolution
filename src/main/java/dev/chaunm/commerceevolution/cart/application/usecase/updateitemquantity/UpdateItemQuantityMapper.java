package dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity;

import dev.chaunm.commerceevolution.cart.presentation.cart.updateitemquantity.UpdateItemQuantityRequest;
import dev.chaunm.commerceevolution.cart.presentation.cart.updateitemquantity.UpdateItemQuantityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateItemQuantityMapper {

    @Mapping(target = "cartItemId", source = "cartItemId")
    @Mapping(target = "quantity", source = "request.quantity")
    UpdateItemQuantityCommand toCommand(UUID cartItemId, UpdateItemQuantityRequest request);

    UpdateItemQuantityResponse toResponse(UpdateItemQuantityResult result);
}
