package dev.chaunm.commerceevolution.order.application.usecase.completeorder;

public interface CompleteOrderUseCase {
    CompleteOrderResult completeOrder(CompleteOrderCommand command);
}
