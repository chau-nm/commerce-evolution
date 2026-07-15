package dev.chaunm.commerceevolution.order.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.order.domain.model.OrderItem;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderItemId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.order.infrastructure.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem toDomain(OrderItemEntity entity);

    OrderItemEntity toEntity(OrderItem domain);

    default UUID toUuid(OrderItemId id) {
        return id == null ? null : id.value();
    }

    default OrderItemId toOrderItemId(UUID value) {
        return value == null ? null : new OrderItemId(value);
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

    default long toAmount(Money money) {
        return money == null ? 0 : money.amount();
    }

    default Money toMoney(long value) {
        return new Money(value);
    }
}
