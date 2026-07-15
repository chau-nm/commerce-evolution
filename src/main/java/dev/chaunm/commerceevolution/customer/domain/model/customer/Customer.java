package dev.chaunm.commerceevolution.customer.domain.model.customer;

import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressAddedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressRemovedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressUpdatedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerDefaultAddressChangedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.customer.CustomerProfileUpdatedEvent;
import dev.chaunm.commerceevolution.customer.domain.exception.address.AddressNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.exception.address.DefaultAddressRemovalException;
import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
import dev.chaunm.commerceevolution.customer.domain.model.address.valueobject.AddressId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.CustomerId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import dev.chaunm.commerceevolution.shared.domain.model.AggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends AggregateRoot {

    @Getter
    private final CustomerId id;
    @Getter
    private final AccountId accountId;
    @Getter
    private FullName fullName;
    @Getter
    private PhoneNumber phoneNumber;
    @Getter
    private LocalDate birthday;
    @Getter
    private Gender gender;
    private final List<Address> addresses;

    public Customer(
            CustomerId id,
            AccountId accountId,
            FullName fullName,
            PhoneNumber phoneNumber,
            LocalDate birthday,
            Gender gender,
            List<Address> addresses
    ) {
        this.id = id;
        this.accountId = accountId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
        this.addresses = new ArrayList<>(addresses);
    }

    public void updateProfile(FullName fullName, PhoneNumber phoneNumber, LocalDate birthday, Gender gender) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
        registerEvent(new CustomerProfileUpdatedEvent(this.id, this.fullName, this.phoneNumber, this.birthday, this.gender));
    }

    public Address addAddress(
            String recipientName,
            PhoneNumber phoneNumber,
            String province,
            String district,
            String ward,
            String street,
            String postalCode,
            boolean isDefault
    ) {
        if (isDefault) {
            addresses.forEach(Address::unmarkDefault);
        }

        Address address = new Address(
                AddressId.generate(),
                recipientName,
                phoneNumber,
                province,
                district,
                ward,
                street,
                postalCode,
                isDefault
        );
        addresses.add(address);
        registerEvent(new CustomerAddressAddedEvent(this.id, address.getId()));

        return address;
    }

    public Address updateAddress(
            AddressId addressId,
            String recipientName,
            PhoneNumber phoneNumber,
            String province,
            String district,
            String ward,
            String street,
            String postalCode
    ) {
        Address address = findAddress(addressId);
        address.update(recipientName, phoneNumber, province, district, ward, street, postalCode);
        registerEvent(new CustomerAddressUpdatedEvent(this.id, address.getId()));

        return address;
    }

    public void removeAddress(AddressId addressId) {
        Address address = findAddress(addressId);

        if (address.isDefault() && addresses.size() > 1) {
            throw new DefaultAddressRemovalException();
        }

        addresses.remove(address);
        registerEvent(new CustomerAddressRemovedEvent(this.id, addressId));
    }

    public Address setDefaultAddress(AddressId addressId) {
        Address address = findAddress(addressId);

        addresses.forEach(Address::unmarkDefault);
        address.markDefault();
        registerEvent(new CustomerDefaultAddressChangedEvent(this.id, address.getId()));

        return address;
    }

    private Address findAddress(AddressId addressId) {
        return addresses.stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(AddressNotFoundException::new);
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }
}
