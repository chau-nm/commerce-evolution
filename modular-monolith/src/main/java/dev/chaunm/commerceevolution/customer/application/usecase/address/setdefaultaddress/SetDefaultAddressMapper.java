package dev.chaunm.commerceevolution.customer.application.usecase.address.setdefaultaddress;

import dev.chaunm.commerceevolution.customer.presentation.address.setdefaultaddress.SetDefaultAddressResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SetDefaultAddressMapper {

    SetDefaultAddressCommand toCommand(UUID addressId);

    SetDefaultAddressResponse toResponse(SetDefaultAddressResult result);
}
