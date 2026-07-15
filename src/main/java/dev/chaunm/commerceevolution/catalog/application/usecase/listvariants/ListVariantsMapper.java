package dev.chaunm.commerceevolution.catalog.application.usecase.listvariants;

import dev.chaunm.commerceevolution.catalog.presentation.listvariants.ListVariantsResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ListVariantsMapper {
    ListVariantsCommand toCommand(UUID productId);
    ListVariantsResponse toResponse(ListVariantsResult result);
}
