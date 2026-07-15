package dev.chaunm.commerceevolution.order.application.usecase.getmyorders;

import dev.chaunm.commerceevolution.shared.application.pagination.PaginationResult;

public interface GetMyOrdersUseCase {
    PaginationResult<OrderSummaryItem> getMyOrders(GetMyOrdersCommand command);
}
