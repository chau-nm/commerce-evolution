package dev.chaunm.commerceevolution.order.presentation.order.placeorder;

import jakarta.validation.constraints.NotBlank;

public record PlaceOrderRequest(
        @NotBlank
        String recipientName,
        @NotBlank
        String recipientPhone,
        @NotBlank
        String province,
        @NotBlank
        String district,
        @NotBlank
        String ward,
        @NotBlank
        String street,
        String postalCode
) {}
