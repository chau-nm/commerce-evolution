package dev.chaunm.commerceevolution.customer.domain.model.customer;

import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressAddedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressRemovedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerAddressUpdatedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.address.CustomerDefaultAddressChangedEvent;
import dev.chaunm.commerceevolution.customer.domain.event.customer.CustomerProfileUpdatedEvent;
import dev.chaunm.commerceevolution.customer.domain.exception.address.AddressNotFoundException;
import dev.chaunm.commerceevolution.customer.domain.exception.address.DefaultAddressRemovalException;
import dev.chaunm.commerceevolution.customer.domain.factory.customer.CustomerFactory;
import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
import dev.chaunm.commerceevolution.customer.domain.model.address.valueobject.AddressId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.create(
                new AccountId(UUID.randomUUID()),
                new FullName("Nguyen Van A"),
                new PhoneNumber("0901234567"),
                LocalDate.of(1990, 1, 1),
                Gender.MALE
        );
        customer.clearDomainEvents();
    }

    @Test
    void updateProfileChangesFieldsAndRegistersEvent() {
        FullName newName = new FullName("Nguyen Van B");
        PhoneNumber newPhone = new PhoneNumber("0987654321");
        LocalDate newBirthday = LocalDate.of(1995, 5, 5);

        customer.updateProfile(newName, newPhone, newBirthday, Gender.FEMALE);

        assertThat(customer.getFullName()).isEqualTo(newName);
        assertThat(customer.getPhoneNumber()).isEqualTo(newPhone);
        assertThat(customer.getBirthday()).isEqualTo(newBirthday);
        assertThat(customer.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(customer.domainEvents())
                .singleElement()
                .isInstanceOf(CustomerProfileUpdatedEvent.class);
    }

    @Test
    void addAddressAppendsToListAndRegistersEvent() {
        Address address = addSampleAddress("Home", false);

        assertThat(customer.getAddresses()).containsExactly(address);
        assertThat(customer.domainEvents())
                .singleElement()
                .isInstanceOf(CustomerAddressAddedEvent.class);
    }

    @Test
    void addingADefaultAddressUnmarksThePreviousDefault() {
        Address first = addSampleAddress("Home", true);
        customer.clearDomainEvents();

        Address second = addSampleAddress("Office", true);

        assertThat(customer.getAddresses())
                .filteredOn(Address::isDefault)
                .containsExactly(second);
        assertThat(first.isDefault()).isFalse();
    }

    @Test
    void firstAddressBecomesDefaultEvenWhenNotRequested() {
        Address address = addSampleAddress("Home", false);

        assertThat(address.isDefault()).isTrue();
    }

    @Test
    void addingANonDefaultAddressDoesNotAffectExistingDefault() {
        Address first = addSampleAddress("Home", true);
        customer.clearDomainEvents();

        addSampleAddress("Office", false);

        assertThat(first.isDefault()).isTrue();
    }

    @Test
    void updateAddressChangesFieldsAndRegistersEvent() {
        Address address = addSampleAddress("Home", false);
        customer.clearDomainEvents();

        PhoneNumber newPhone = new PhoneNumber("0987654321");
        Address updated = customer.updateAddress(
                address.getId(),
                "New Recipient",
                newPhone,
                "New Province",
                "New District",
                "New Ward",
                "New Street",
                "70000"
        );

        assertThat(updated.getRecipientName()).isEqualTo("New Recipient");
        assertThat(updated.getPhoneNumber()).isEqualTo(newPhone);
        assertThat(updated.getProvince()).isEqualTo("New Province");
        assertThat(customer.domainEvents())
                .singleElement()
                .isInstanceOf(CustomerAddressUpdatedEvent.class);
    }

    @Test
    void updateAddressThrowsWhenAddressDoesNotExist() {
        assertThatThrownBy(() -> customer.updateAddress(
                AddressId.generate(), "x", new PhoneNumber("0901234567"), "x", "x", "x", "x", "x"
        )).isInstanceOf(AddressNotFoundException.class);
    }

    @Test
    void removingANonDefaultAddressSucceeds() {
        addSampleAddress("Home", true);
        Address office = addSampleAddress("Office", false);
        customer.clearDomainEvents();

        customer.removeAddress(office.getId());

        assertThat(customer.getAddresses()).doesNotContain(office);
        assertThat(customer.domainEvents())
                .singleElement()
                .isInstanceOf(CustomerAddressRemovedEvent.class);
    }

    @Test
    void removingTheDefaultAddressIsRejectedWhileOtherAddressesRemain() {
        Address home = addSampleAddress("Home", true);
        addSampleAddress("Office", false);

        assertThatThrownBy(() -> customer.removeAddress(home.getId()))
                .isInstanceOf(DefaultAddressRemovalException.class);
        assertThat(customer.getAddresses()).contains(home);
    }

    @Test
    void removingTheOnlyRemainingAddressIsAllowedEvenIfItIsDefault() {
        Address onlyAddress = addSampleAddress("Home", true);

        customer.removeAddress(onlyAddress.getId());

        assertThat(customer.getAddresses()).isEmpty();
    }

    @Test
    void removeAddressThrowsWhenAddressDoesNotExist() {
        assertThatThrownBy(() -> customer.removeAddress(AddressId.generate()))
                .isInstanceOf(AddressNotFoundException.class);
    }

    @Test
    void setDefaultAddressMarksTargetAndUnmarksPrevious() {
        Address home = addSampleAddress("Home", true);
        Address office = addSampleAddress("Office", false);
        customer.clearDomainEvents();

        Address result = customer.setDefaultAddress(office.getId());

        assertThat(result.isDefault()).isTrue();
        assertThat(home.isDefault()).isFalse();
        assertThat(customer.domainEvents())
                .singleElement()
                .isInstanceOf(CustomerDefaultAddressChangedEvent.class);
    }

    @Test
    void setDefaultAddressThrowsWhenAddressDoesNotExist() {
        assertThatThrownBy(() -> customer.setDefaultAddress(AddressId.generate()))
                .isInstanceOf(AddressNotFoundException.class);
    }

    private Address addSampleAddress(String recipientName, boolean isDefault) {
        return customer.addAddress(
                recipientName,
                new PhoneNumber("0901234567"),
                "Province",
                "District",
                "Ward",
                "Street",
                "70000",
                isDefault
        );
    }
}
