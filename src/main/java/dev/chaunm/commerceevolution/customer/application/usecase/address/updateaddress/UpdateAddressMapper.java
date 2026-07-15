package dev.chaunm.commerceevolution.customer.application.usecase.address.updateaddress;

import dev.chaunm.commerceevolution.customer.presentation.address.updateaddress.UpdateAddressRequest;
import dev.chaunm.commerceevolution.customer.presentation.address.updateaddress.UpdateAddressResponse;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateAddressMapper {

    @Mapping(target = "addressId", source = "addressId")
    @Mapping(target = "recipientName", source = "request.recipientName")
    @Mapping(target = "phoneNumber", source = "request.phoneNumber")
    @Mapping(target = "province", source = "request.province")
    @Mapping(target = "district", source = "request.district")
    @Mapping(target = "ward", source = "request.ward")
    @Mapping(target = "street", source = "request.street")
    @Mapping(target = "postalCode", source = "request.postalCode")
    UpdateAddressCommand toCommand(UUID addressId, UpdateAddressRequest request);

    UpdateAddressResponse toResponse(UpdateAddressResult result);
}
