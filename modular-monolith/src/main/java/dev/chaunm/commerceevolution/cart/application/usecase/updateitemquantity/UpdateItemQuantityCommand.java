package dev.chaunm.commerceevolution.cart.application.usecase.updateitemquantity;

import java.util.UUID;

public record UpdateItemQuantityCommand(
        UUID cartItemId,
        int quantity
) {}
