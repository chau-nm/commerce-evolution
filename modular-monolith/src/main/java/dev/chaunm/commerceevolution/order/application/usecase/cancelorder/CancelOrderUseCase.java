package dev.chaunm.commerceevolution.order.application.usecase.cancelorder;

public interface CancelOrderUseCase {
    CancelOrderResult cancelOrder(CancelOrderCommand command);
}
