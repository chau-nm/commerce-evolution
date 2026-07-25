package dev.chaunm.commerceevolution.cart.application.usecase.removeitem;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RemoveItemMapper {

    RemoveItemCommand toCommand(UUID cartItemId);
}
