package dev.chaunm.commerceevolution.cart.application.usecase.getcart;

import dev.chaunm.commerceevolution.cart.presentation.cart.getcart.GetCartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetCartMapper {

    GetCartResponse toResponse(GetCartResult result);
}
