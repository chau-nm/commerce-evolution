package dev.chaunm.commerceevolution.payment.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.payment.domain.model.Payment;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.OrderId;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentId;
import dev.chaunm.commerceevolution.payment.domain.model.valueobject.PaymentStatus;
import dev.chaunm.commerceevolution.payment.infrastructure.persistence.entity.PaymentEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toDomain(PaymentEntity entity);

    PaymentEntity toEntity(Payment domain);

    default UUID toUuid(PaymentId id) {
        return id == null ? null : id.value();
    }

    default PaymentId toPaymentId(UUID value) {
        return value == null ? null : new PaymentId(value);
    }

    default UUID toUuid(OrderId id) {
        return id == null ? null : id.value();
    }

    default OrderId toOrderId(UUID value) {
        return value == null ? null : new OrderId(value);
    }

    default long toAmountValue(Money money) {
        return money == null ? 0 : money.amount();
    }

    default Money toMoney(long value) {
        return new Money(value);
    }

    default String toStatusValue(PaymentStatus status) {
        return status == null ? null : status.name();
    }

    default PaymentStatus toPaymentStatus(String value) {
        return value == null ? null : PaymentStatus.valueOf(value);
    }
}
