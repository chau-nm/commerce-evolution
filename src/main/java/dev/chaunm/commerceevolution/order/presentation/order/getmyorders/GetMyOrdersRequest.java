package dev.chaunm.commerceevolution.order.presentation.order.getmyorders;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

public record GetMyOrdersRequest(
        PaginationRequest pagination
) {}
