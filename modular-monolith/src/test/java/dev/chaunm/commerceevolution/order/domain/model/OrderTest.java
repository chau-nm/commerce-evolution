package dev.chaunm.commerceevolution.order.domain.model;

import dev.chaunm.commerceevolution.order.domain.event.OrderCancelledEvent;
import dev.chaunm.commerceevolution.order.domain.event.OrderCompletedEvent;
import dev.chaunm.commerceevolution.order.domain.event.OrderCreatedEvent;
import dev.chaunm.commerceevolution.order.domain.event.OrderPaidEvent;
import dev.chaunm.commerceevolution.order.domain.event.OrderShippingStartedEvent;
import dev.chaunm.commerceevolution.order.domain.exception.EmptyOrderException;
import dev.chaunm.commerceevolution.order.domain.exception.InvalidOrderStatusTransitionException;
import dev.chaunm.commerceevolution.order.domain.exception.InvalidQuantityException;
import dev.chaunm.commerceevolution.order.domain.factory.OrderFactory;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderStatus;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.ShippingAddress;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.VariantId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    private ShippingAddress shippingAddress;

    @BeforeEach
    void setUp() {
        shippingAddress = new ShippingAddress(
                "Nguyen Van A", "0901234567", "HCM", "District 1", "Ben Nghe", "123 Le Loi", "700000"
        );
    }

    private OrderItem sampleItem(long unitPrice, int quantity) {
        return OrderItem.snapshot(
                new VariantId(UUID.randomUUID()),
                "Product A",
                "Variant A",
                new Money(unitPrice),
                new Quantity(quantity)
        );
    }

    @Test
    void createComputesTotalAmountAsSumOfSubtotalsAndRegistersEvent() {
        OrderItem item1 = sampleItem(100_000, 2);
        OrderItem item2 = sampleItem(50_000, 3);

        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(item1, item2));

        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getTotalAmount()).isEqualTo(new Money(200_000 + 150_000));
        assertThat(order.getItems()).containsExactly(item1, item2);
        assertThat(order.domainEvents())
                .singleElement()
                .isInstanceOf(OrderCreatedEvent.class);
    }

    @Test
    void createRejectsAnEmptyItemList() {
        assertThatThrownBy(() -> OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of()))
                .isInstanceOf(EmptyOrderException.class);
    }

    @Test
    void orderItemSnapshotComputesSubtotal() {
        OrderItem item = sampleItem(15_000, 4);

        assertThat(item.getSubtotal()).isEqualTo(new Money(60_000));
    }

    @Test
    void quantityRejectsNonPositiveValues() {
        assertThatThrownBy(() -> new Quantity(0)).isInstanceOf(InvalidQuantityException.class);
    }

    @Test
    void markPaidTransitionsFromPendingAndRegistersEvent() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));
        order.clearDomainEvents();

        order.markPaid();

        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(order.domainEvents())
                .singleElement()
                .isInstanceOf(OrderPaidEvent.class);
    }

    @Test
    void markPaidRejectsWhenNotPending() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));
        order.markPaid();

        assertThatThrownBy(order::markPaid).isInstanceOf(InvalidOrderStatusTransitionException.class);
    }

    @Test
    void startShippingTransitionsFromPaidAndRegistersEvent() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));
        order.markPaid();
        order.clearDomainEvents();

        order.startShipping();

        assertThat(order.getStatus()).isEqualTo(OrderStatus.SHIPPING);
        assertThat(order.domainEvents())
                .singleElement()
                .isInstanceOf(OrderShippingStartedEvent.class);
    }

    @Test
    void startShippingRejectsWhenNotPaid() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));

        assertThatThrownBy(order::startShipping).isInstanceOf(InvalidOrderStatusTransitionException.class);
    }

    @Test
    void completeTransitionsFromShippingAndRegistersEvent() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));
        order.markPaid();
        order.startShipping();
        order.clearDomainEvents();

        order.complete();

        assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(order.domainEvents())
                .singleElement()
                .isInstanceOf(OrderCompletedEvent.class);
    }

    @Test
    void completeRejectsWhenNotShipping() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));

        assertThatThrownBy(order::complete).isInstanceOf(InvalidOrderStatusTransitionException.class);
    }

    @Test
    void cancelTransitionsFromPendingAndRegistersEvent() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));
        order.clearDomainEvents();

        order.cancel();

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.domainEvents())
                .singleElement()
                .isInstanceOf(OrderCancelledEvent.class);
    }

    @Test
    void cancelRejectsWhenAlreadyPaid() {
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), shippingAddress, List.of(sampleItem(10_000, 1)));
        order.markPaid();

        assertThatThrownBy(order::cancel).isInstanceOf(InvalidOrderStatusTransitionException.class);
    }
}
