package dev.chaunm.commerceevolution.cart.application.usecase.removeitem;

import java.util.UUID;

public record RemoveItemCommand(
        UUID cartItemId
) {}
