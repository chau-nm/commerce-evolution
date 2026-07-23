package dev.chaunm.commerceevolution.customer.application.usecase.customer.createcustomer;

import dev.chaunm.commerceevolution.customer.presentation.customer.createcustomer.CreateCustomerRequest;
import dev.chaunm.commerceevolution.customer.presentation.customer.createcustomer.CreateCustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateCustomerMapper {

    CreateCustomerCommand toCommand(CreateCustomerRequest request);

    CreateCustomerResponse toResponse(CreateCustomerResult result);
}
