package dev.chaunm.commerceevolution.order.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderNumber;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.ShippingAddress;
import dev.chaunm.commerceevolution.order.infrastructure.persistence.entity.OrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemMapper.class}
)
public interface OrderMapper {

    @Mapping(target = "shippingAddress", expression = "java(toShippingAddress(entity))")
    Order toDomain(OrderEntity entity);

    @Mapping(target = "recipientName", source = "shippingAddress.recipientName")
    @Mapping(target = "recipientPhone", source = "shippingAddress.recipientPhone")
    @Mapping(target = "province", source = "shippingAddress.province")
    @Mapping(target = "district", source = "shippingAddress.district")
    @Mapping(target = "ward", source = "shippingAddress.ward")
    @Mapping(target = "street", source = "shippingAddress.street")
    @Mapping(target = "postalCode", source = "shippingAddress.postalCode")
    OrderEntity toEntity(Order domain);

    @AfterMapping
    default void linkChildren(@MappingTarget OrderEntity entity) {
        entity.getItems().forEach(item -> item.setOrder(entity));
    }

    default ShippingAddress toShippingAddress(OrderEntity entity) {
        return new ShippingAddress(
                entity.getRecipientName(),
                entity.getRecipientPhone(),
                entity.getProvince(),
                entity.getDistrict(),
                entity.getWard(),
                entity.getStreet(),
                entity.getPostalCode()
        );
    }

    default UUID toUuid(OrderId id) {
        return id == null ? null : id.value();
    }

    default OrderId toOrderId(UUID value) {
        return value == null ? null : new OrderId(value);
    }

    default UUID toUuid(CustomerId id) {
        return id == null ? null : id.value();
    }

    default CustomerId toCustomerId(UUID value) {
        return value == null ? null : new CustomerId(value);
    }

    default String toRaw(OrderNumber orderNumber) {
        return orderNumber == null ? null : orderNumber.value();
    }

    default OrderNumber toOrderNumber(String value) {
        return value == null ? null : new OrderNumber(value);
    }
}
