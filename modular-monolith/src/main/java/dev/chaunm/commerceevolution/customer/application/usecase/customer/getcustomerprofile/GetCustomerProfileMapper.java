package dev.chaunm.commerceevolution.customer.application.usecase.customer.getcustomerprofile;

import dev.chaunm.commerceevolution.customer.presentation.customer.getcustomerprofile.GetCustomerProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetCustomerProfileMapper {

    GetCustomerProfileResponse toResponse(GetCustomerProfileResult result);
}
