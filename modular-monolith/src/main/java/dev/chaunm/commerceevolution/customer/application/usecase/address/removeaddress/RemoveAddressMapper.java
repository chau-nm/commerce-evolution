package dev.chaunm.commerceevolution.customer.application.usecase.address.removeaddress;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RemoveAddressMapper {

    RemoveAddressCommand toCommand(UUID addressId);
}
