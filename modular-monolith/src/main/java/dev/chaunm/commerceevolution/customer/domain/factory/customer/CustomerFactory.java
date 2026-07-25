package dev.chaunm.commerceevolution.customer.domain.factory.customer;

import dev.chaunm.commerceevolution.customer.domain.event.customer.CustomerCreatedEvent;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.AccountId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.CustomerId;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.FullName;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.Gender;
import dev.chaunm.commerceevolution.customer.domain.model.customer.valueobject.PhoneNumber;

import java.time.LocalDate;
import java.util.List;

public class CustomerFactory {

    public static Customer create(
            AccountId accountId,
            FullName fullName,
            PhoneNumber phoneNumber,
            LocalDate birthday,
            Gender gender
    ) {
        Customer customer = new Customer(
                CustomerId.generate(),
                accountId,
                fullName,
                phoneNumber,
                birthday,
                gender,
                List.of()
        );

        customer.registerEvent(new CustomerCreatedEvent(customer.getId(), customer.getAccountId()));

        return customer;
    }
}
