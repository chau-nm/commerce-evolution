package dev.chaunm.commerceevolution.order.application.usecase.startshipping;

import dev.chaunm.commerceevolution.order.presentation.order.startshipping.StartShippingResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StartShippingMapper {

    StartShippingCommand toCommand(UUID orderId);

    StartShippingResponse toResponse(StartShippingResult result);
}
