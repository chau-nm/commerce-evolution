package dev.chaunm.commerceevolution.order.domain.model;

import dev.chaunm.commerceevolution.order.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderItemId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.VariantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItem {
    private final OrderItemId id;
    private final VariantId variantId;
    private final String productName;
    private final String variantName;
    private final Money unitPrice;
    private final Quantity quantity;
    private final Money subtotal;

    public static OrderItem snapshot(
            VariantId variantId,
            String productName,
            String variantName,
            Money unitPrice,
            Quantity quantity
    ) {
        Money subtotal = unitPrice.multiply(quantity.value());
        return new OrderItem(OrderItemId.generate(), variantId, productName, variantName, unitPrice, quantity, subtotal);
    }
}
