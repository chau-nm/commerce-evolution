package dev.chaunm.commerceevolution.order.domain.model.valueobject;

import dev.chaunm.commerceevolution.order.domain.exception.InvalidShippingAddressException;

public record ShippingAddress(
        String recipientName,
        String recipientPhone,
        String province,
        String district,
        String ward,
        String street,
        String postalCode
) {

    public ShippingAddress {
        if (recipientName == null || recipientName.isBlank()) {
            throw new InvalidShippingAddressException("recipientName is required");
        }
        if (recipientPhone == null || recipientPhone.isBlank()) {
            throw new InvalidShippingAddressException("recipientPhone is required");
        }
        if (province == null || province.isBlank()) {
            throw new InvalidShippingAddressException("province is required");
        }
        if (district == null || district.isBlank()) {
            throw new InvalidShippingAddressException("district is required");
        }
        if (ward == null || ward.isBlank()) {
            throw new InvalidShippingAddressException("ward is required");
        }
        if (street == null || street.isBlank()) {
            throw new InvalidShippingAddressException("street is required");
        }
    }
}
