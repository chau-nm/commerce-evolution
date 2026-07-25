package dev.chaunm.commerceevolution.customer.application.usecase.address.addaddress;

import dev.chaunm.commerceevolution.customer.presentation.address.addaddress.AddAddressRequest;
import dev.chaunm.commerceevolution.customer.presentation.address.addaddress.AddAddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddAddressMapper {

    AddAddressCommand toCommand(AddAddressRequest request);

    AddAddressResponse toResponse(AddAddressResult result);
}
