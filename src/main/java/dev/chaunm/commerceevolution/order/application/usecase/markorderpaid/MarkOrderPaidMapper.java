package dev.chaunm.commerceevolution.order.application.usecase.markorderpaid;

import dev.chaunm.commerceevolution.order.presentation.order.markorderpaid.MarkOrderPaidResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MarkOrderPaidMapper {

    MarkOrderPaidCommand toCommand(UUID orderId);

    MarkOrderPaidResponse toResponse(MarkOrderPaidResult result);
}
