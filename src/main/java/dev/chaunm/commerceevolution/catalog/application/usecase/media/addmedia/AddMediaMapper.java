package dev.chaunm.commerceevolution.catalog.application.usecase.media.addmedia;

import dev.chaunm.commerceevolution.catalog.presentation.media.addmedia.AddMediaRequest;
import dev.chaunm.commerceevolution.catalog.presentation.media.addmedia.AddMediaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AddMediaMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "url", source = "request.url")
    @Mapping(target = "primary", source = "request.primary")
    AddMediaCommand toCommand(UUID productId, AddMediaRequest request);

    AddMediaResponse toResponse(AddMediaResult result);
}
