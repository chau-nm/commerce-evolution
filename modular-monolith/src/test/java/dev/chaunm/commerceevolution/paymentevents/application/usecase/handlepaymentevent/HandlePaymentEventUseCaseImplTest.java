package dev.chaunm.commerceevolution.paymentevents.application.usecase.handlepaymentevent;

import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.createinventory.CreateInventoryUseCase;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockUseCase;
import dev.chaunm.commerceevolution.notification.infrastructure.persistence.repository.JpaNotificationRepository;
import dev.chaunm.commerceevolution.order.domain.factory.OrderFactory;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.OrderItem;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.OrderStatus;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.ShippingAddress;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.paymentevents.infrastructure.persistence.repository.ProcessedPaymentEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exercises the inbound side of the payment-service integration end to end against real
 * beans/DB: this is the single place order's status transition and notification creation get
 * triggered now that payment lives in a separate process and can no longer call either
 * use case in-process.
 */
@SpringBootTest
@Transactional
class HandlePaymentEventUseCaseImplTest {

    @Autowired
    private HandlePaymentEventUseCase handlePaymentEventUseCase;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CreateInventoryUseCase createInventoryUseCase;

    @Autowired
    private ReserveStockUseCase reserveStockUseCase;

    @Autowired
    private JpaNotificationRepository jpaNotificationRepository;

    @Autowired
    private ProcessedPaymentEventRepository processedPaymentEventRepository;

    private long notificationCountFor(Order order) {
        return jpaNotificationRepository
                .findByRecipientId(order.getCustomerId().value(), Pageable.ofSize(20))
                .getTotalElements();
    }

    private Order placeOrderWithReservedStock() {
        VariantId variantId = new VariantId(UUID.randomUUID());
        createInventoryUseCase.create(new CreateInventoryCommand(variantId.value(), 10));
        reserveStockUseCase.reserve(new ReserveStockCommand(variantId.value(), 2));

        OrderItem item = OrderItem.snapshot(variantId, "Product A", "Variant A", new Money(100_000), new Quantity(2));
        ShippingAddress address = new ShippingAddress(
                "Nguyen Van A", "0901234567", "HCM", "District 1", "Ben Nghe", "123 Le Loi", "700000");
        Order order = OrderFactory.create(new CustomerId(UUID.randomUUID()), address, List.of(item));
        order.clearDomainEvents();
        return orderRepository.save(order);
    }

    @Test
    void paidEventMarksOrderPaidAndCreatesSuccessNotification() {
        Order order = placeOrderWithReservedStock();
        UUID paymentId = UUID.randomUUID();

        handlePaymentEventUseCase.handle(new HandlePaymentEventCommand(paymentId, order.getId().value(), "PAID"));

        Order reloaded = orderRepository.findById(order.getId()).orElseThrow();
        assertThat(reloaded.getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(processedPaymentEventRepository.existsById(paymentId)).isTrue();
        assertThat(notificationCountFor(order)).isEqualTo(1);
    }

    @Test
    void failedEventCreatesFailureNotificationWithoutTouchingOrderStatus() {
        Order order = placeOrderWithReservedStock();
        UUID paymentId = UUID.randomUUID();

        handlePaymentEventUseCase.handle(new HandlePaymentEventCommand(paymentId, order.getId().value(), "FAILED"));

        Order reloaded = orderRepository.findById(order.getId()).orElseThrow();
        assertThat(reloaded.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(notificationCountFor(order)).isEqualTo(1);
    }

    @Test
    void redeliveredPaidEventIsIgnoredAndDoesNotDuplicateNotifications() {
        Order order = placeOrderWithReservedStock();
        UUID paymentId = UUID.randomUUID();
        HandlePaymentEventCommand command = new HandlePaymentEventCommand(paymentId, order.getId().value(), "PAID");

        handlePaymentEventUseCase.handle(command);
        handlePaymentEventUseCase.handle(command);

        Order reloaded = orderRepository.findById(order.getId()).orElseThrow();
        assertThat(reloaded.getStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(notificationCountFor(order)).isEqualTo(1);
    }
}
