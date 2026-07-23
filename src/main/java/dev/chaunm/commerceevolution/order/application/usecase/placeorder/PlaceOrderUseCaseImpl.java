package dev.chaunm.commerceevolution.order.application.usecase.placeorder;

import dev.chaunm.commerceevolution.cart.application.usecase.clearcart.ClearCartUseCase;
import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartResult;
import dev.chaunm.commerceevolution.cart.application.usecase.getcart.GetCartUseCase;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantsnapshot.GetVariantSnapshotUseCase;
import dev.chaunm.commerceevolution.catalog.application.usecase.variant.getvariantsnapshot.VariantSnapshotResult;
import dev.chaunm.commerceevolution.customer.application.port.CustomerDirectory;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockCommand;
import dev.chaunm.commerceevolution.inventory.application.usecase.reservestock.ReserveStockUseCase;
import dev.chaunm.commerceevolution.inventory.domain.exception.InsufficientAvailableStockException;
import dev.chaunm.commerceevolution.inventory.domain.exception.InventoryNotFoundException;
import dev.chaunm.commerceevolution.order.domain.exception.CustomerNotFoundException;
import dev.chaunm.commerceevolution.order.domain.exception.EmptyOrderException;
import dev.chaunm.commerceevolution.order.domain.exception.InsufficientStockException;
import dev.chaunm.commerceevolution.order.domain.exception.VariantNotFoundException;
import dev.chaunm.commerceevolution.order.domain.factory.OrderFactory;
import dev.chaunm.commerceevolution.order.domain.model.Order;
import dev.chaunm.commerceevolution.order.domain.model.OrderItem;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.CustomerId;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Money;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.Quantity;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.ShippingAddress;
import dev.chaunm.commerceevolution.order.domain.model.valueobject.VariantId;
import dev.chaunm.commerceevolution.order.domain.repository.OrderRepository;
import dev.chaunm.commerceevolution.shared.application.currentuser.CurrentUserProvider;
import dev.chaunm.commerceevolution.shared.domain.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceOrderUseCaseImpl implements PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerDirectory customerDirectory;
    private final GetCartUseCase getCartUseCase;
    private final ClearCartUseCase clearCartUseCase;
    private final GetVariantSnapshotUseCase getVariantSnapshotUseCase;
    private final ReserveStockUseCase reserveStockUseCase;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public PlaceOrderResult placeOrder(PlaceOrderCommand command) {
        UUID accountId = currentUserProvider.getCurrentUser().accountId();
        CustomerId customerId = new CustomerId(
                customerDirectory.findCustomerIdByAccountId(accountId)
                        .orElseThrow(CustomerNotFoundException::new));

        GetCartResult cart = getCartUseCase.getCart();
        if (cart.items().isEmpty()) {
            throw new EmptyOrderException();
        }

        ShippingAddress shippingAddress = new ShippingAddress(
                command.recipientName(),
                command.recipientPhone(),
                command.province(),
                command.district(),
                command.ward(),
                command.street(),
                command.postalCode()
        );

        var orderItems = cart.items().stream()
                .map(this::toOrderItem)
                .toList();

        Order order = OrderFactory.create(customerId, shippingAddress, orderItems);

        orderRepository.save(order);
        order.domainEvents().forEach(domainEventPublisher::publish);

        clearCartUseCase.clearCart();

        return toResult(order);
    }

    private OrderItem toOrderItem(GetCartResult.CartItemResult cartItem) {
        VariantSnapshotResult variant = getVariantSnapshotUseCase.getByVariantId(cartItem.variantId())
                .filter(VariantSnapshotResult::purchasable)
                .orElseThrow(VariantNotFoundException::new);

        try {
            reserveStockUseCase.reserve(new ReserveStockCommand(cartItem.variantId(), cartItem.quantity()));
        } catch (InventoryNotFoundException | InsufficientAvailableStockException e) {
            throw new InsufficientStockException(cartItem.variantId(), cartItem.quantity());
        }

        return OrderItem.snapshot(
                new VariantId(cartItem.variantId()),
                variant.productName(),
                variant.variantName(),
                new Money(variant.price()),
                new Quantity(cartItem.quantity())
        );
    }

    private PlaceOrderResult toResult(Order order) {
        return new PlaceOrderResult(
                order.getId().value(),
                order.getOrderNumber().value(),
                order.getStatus().name(),
                order.getTotalAmount().amount(),
                order.getItems().stream()
                        .map(item -> new PlaceOrderResult.OrderItemResult(
                                item.getId().value(),
                                item.getVariantId().value(),
                                item.getProductName(),
                                item.getVariantName(),
                                item.getUnitPrice().amount(),
                                item.getQuantity().value(),
                                item.getSubtotal().amount()
                        ))
                        .toList()
        );
    }
}
