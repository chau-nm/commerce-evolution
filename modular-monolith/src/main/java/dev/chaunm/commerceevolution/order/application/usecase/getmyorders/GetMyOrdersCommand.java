package dev.chaunm.commerceevolution.order.application.usecase.getmyorders;

import dev.chaunm.commerceevolution.shared.presentation.pagination.PaginationRequest;

public record GetMyOrdersCommand(PaginationRequest pagination) {}
