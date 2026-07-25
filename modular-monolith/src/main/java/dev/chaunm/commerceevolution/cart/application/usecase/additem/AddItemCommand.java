package dev.chaunm.commerceevolution.cart.application.usecase.additem;

import java.util.UUID;

public record AddItemCommand(
        UUID variantId,
        int quantity
) {}
