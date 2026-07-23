package dev.chaunm.commerceevolution.customer.domain.model.address;

import dev.chaunm.commerceevolution.customer.domain.model.address.valueobject.AddressId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {
    private final AddressId id;
    private String recipientName;
    private PhoneNumber phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String street;
    private String postalCode;
    private boolean isDefault;

    public void update(
            String recipientName,
            PhoneNumber phoneNumber,
            String province,
            String district,
            String ward,
            String street,
            String postalCode
    ) {
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.postalCode = postalCode;
    }

    public void markDefault() {
        this.isDefault = true;
    }

    public void unmarkDefault() {
        this.isDefault = false;
    }
}
