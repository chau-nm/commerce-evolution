package dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.domain.model.address.Address;
import dev.chaunm.commerceevolution.customer.domain.model.customer.Customer;
import dev.chaunm.commerceevolution.customer.presentation.customer.getcustomerprofile.GetCustomerProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetCustomerProfileMapper {

    default GetCustomerProfileResult toResult(Customer customer) {
        return new GetCustomerProfileResult(
                customer.getId().value(),
                customer.getAccountId().value(),
                customer.getFullName().value(),
                customer.getPhoneNumber().value(),
                customer.getBirthday(),
                customer.getGender(),
                customer.getAddresses().stream()
                        .map(this::toAddressItem)
                        .toList()
        );
    }

    default GetCustomerProfileResult.AddressItem toAddressItem(Address address) {
        return new GetCustomerProfileResult.AddressItem(
                address.getId().value(),
                address.getRecipientName(),
                address.getPhoneNumber().value(),
                address.getProvince(),
                address.getDistrict(),
                address.getWard(),
                address.getStreet(),
                address.getPostalCode(),
                address.isDefault()
        );
    }

    GetCustomerProfileResponse toResponse(GetCustomerProfileResult result);
}
