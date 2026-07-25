package dev.chaunm.commerceevolution.customer.application.usecase.customer.updatecustomerprofile;

import dev.chaunm.commerceevolution.customer.presentation.customer.updatecustomerprofile.UpdateCustomerProfileRequest;
import dev.chaunm.commerceevolution.customer.presentation.customer.updatecustomerprofile.UpdateCustomerProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateCustomerProfileMapper {

    UpdateCustomerProfileCommand toCommand(UpdateCustomerProfileRequest request);

    UpdateCustomerProfileResponse toResponse(UpdateCustomerProfileResult result);
}
