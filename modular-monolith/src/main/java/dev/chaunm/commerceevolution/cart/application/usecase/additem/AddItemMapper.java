package dev.chaunm.commerceevolution.cart.application.usecase.additem;

import dev.chaunm.commerceevolution.cart.presentation.cart.additem.AddItemRequest;
import dev.chaunm.commerceevolution.cart.presentation.cart.additem.AddItemResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddItemMapper {

    AddItemCommand toCommand(AddItemRequest request);

    AddItemResponse toResponse(AddItemResult result);
}
