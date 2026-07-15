package dev.chaunm.commerceevolution.cart.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.cart.infrastructure.persistence.entity.CartEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {CartItemMapper.class}
)
public interface CartMapper {

    Cart toDomain(CartEntity entity);

    CartEntity toEntity(Cart domain);

    @AfterMapping
    default void linkChildren(@MappingTarget CartEntity entity) {
        entity.getItems().forEach(item -> item.setCart(entity));
    }

    default UUID toUuid(CartId id) {
        return id == null ? null : id.value();
    }

    default CartId toCartId(UUID value) {
        return value == null ? null : new CartId(value);
    }

    default UUID toUuid(CustomerId id) {
        return id == null ? null : id.value();
    }

    default CustomerId toCustomerId(UUID value) {
        return value == null ? null : new CustomerId(value);
    }
}
