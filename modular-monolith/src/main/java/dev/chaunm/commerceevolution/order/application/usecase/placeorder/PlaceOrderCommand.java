package dev.chaunm.commerceevolution.order.application.usecase.placeorder;

public record PlaceOrderCommand(
        String recipientName,
        String recipientPhone,
        String province,
        String district,
        String ward,
        String street,
        String postalCode
) {}
