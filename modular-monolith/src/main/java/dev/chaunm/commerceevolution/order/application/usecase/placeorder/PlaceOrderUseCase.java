package dev.chaunm.commerceevolution.order.application.usecase.placeorder;

public interface PlaceOrderUseCase {
    PlaceOrderResult placeOrder(PlaceOrderCommand command);
}
