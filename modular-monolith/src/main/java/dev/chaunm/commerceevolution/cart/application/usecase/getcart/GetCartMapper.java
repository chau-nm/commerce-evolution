package dev.chaunm.commerceevolution.cart.application.usecase.getcart;

import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.CartItem;
import dev.chaunm.commerceevolution.cart.presentation.cart.getcart.GetCartResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetCartMapper {

    default GetCartResult toResult(Cart cart) {
        return new GetCartResult(
                cart.getId().value(),
                cart.getCustomerId().value(),
                cart.getItems().stream().map(this::toItemResult).toList(),
                cart.getUpdatedAt()
        );
    }

    default GetCartResult.CartItemResult toItemResult(CartItem item) {
        return new GetCartResult.CartItemResult(
                item.getId().value(),
                item.getVariantId().value(),
                item.getQuantity().value()
        );
    }

    GetCartResponse toResponse(GetCartResult result);
}
