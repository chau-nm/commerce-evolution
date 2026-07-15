package dev.chaunm.commerceevolution.order.application.usecase.placeorder;

import dev.chaunm.commerceevolution.cart.domain.model.Cart;
import dev.chaunm.commerceevolution.cart.domain.model.CartItem;
import dev.chaunm.commerceevolution.cart.domain.repository.CartRepository;
import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.ProductVariant;
import dev.chaunm.commerceevolution.catalog.domain.repository.product.ProductRepository;
import dev.chaunm.commerceevolution.customer.domain.exception.customer.CustomerNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.repository.customer.CustomerRepository;
import dev.chaunm.commerceevolution.inventory.domain.model.Inventory;
import dev.chaunm.commerceevolution.inventory.domain.repository.InventoryRepository;
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

@Service
@RequiredArgsConstructor
public class PlaceOrderUseCaseImpl implements PlaceOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional
    public PlaceOrderResult placeOrder(PlaceOrderCommand command) {
        Customer customer = customerRepository.findByAccountId(new AccountId(currentUserProvider.getCurrentUser().accountId()))
                .orElseThrow(CustomerNotFoundException::new);

        CustomerId customerId = new CustomerId(customer.getId().value());

        Cart cart = cartRepository.findByCustomerId(
                        new dev.chaunm.commerceevolution.cart.domain.model.valueobject.CustomerId(customer.getId().value()))
                .orElseThrow(EmptyOrderException::new);

        if (cart.getItems().isEmpty()) {
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

        var orderItems = cart.getItems().stream()
                .map(this::toOrderItem)
                .toList();

        Order order = OrderFactory.create(customerId, shippingAddress, orderItems);

        orderRepository.save(order);
        order.domainEvents().forEach(domainEventPublisher::publish);

        cart.clear();
        cartRepository.save(cart);
        cart.domainEvents().forEach(domainEventPublisher::publish);

        return toResult(order);
    }

    private OrderItem toOrderItem(CartItem cartItem) {
        var catalogVariantId =
                new dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId(cartItem.getVariantId().value());

        Product product = productRepository.findByVariantId(catalogVariantId)
                .orElseThrow(VariantNotFoundException::new);
        ProductVariant variant = product.getVariant(catalogVariantId);

        var inventoryVariantId =
                new dev.chaunm.commerceevolution.inventory.domain.model.valueobject.VariantId(cartItem.getVariantId().value());

        int requested = cartItem.getQuantity().value();
        int available = inventoryRepository.findByVariantId(inventoryVariantId)
                .map(Inventory::getAvailableQuantity)
                .orElse(0);

        if (available < requested) {
            throw new InsufficientStockException(cartItem.getVariantId().value(), available, requested);
        }

        return OrderItem.snapshot(
                new VariantId(cartItem.getVariantId().value()),
                product.getName().value(),
                variant.getName(),
                new Money(variant.getPrice().amount()),
                new Quantity(requested)
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
