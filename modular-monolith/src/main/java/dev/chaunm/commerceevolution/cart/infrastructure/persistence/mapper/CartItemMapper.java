package dev.chaunm.commerceevolution.cart.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.cart.domain.model.CartItem;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.CartItemId;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.cart.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.cart.infrastructure.persistence.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "quantity", source = "quantity")
    CartItem toDomain(CartItemEntity entity);

    @Mapping(target = "quantity", source = "quantity")
    CartItemEntity toEntity(CartItem domain);

    default UUID toUuid(CartItemId id) {
        return id == null ? null : id.value();
    }

    default CartItemId toCartItemId(UUID value) {
        return value == null ? null : new CartItemId(value);
    }

    default UUID toUuid(VariantId id) {
        return id == null ? null : id.value();
    }

    default VariantId toVariantId(UUID value) {
        return value == null ? null : new VariantId(value);
    }

    default int toQuantityValue(Quantity quantity) {
        return quantity == null ? 0 : quantity.value();
    }

    default Quantity toQuantity(int value) {
        return new Quantity(value);
    }
}
