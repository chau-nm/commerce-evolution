package dev.chaunm.commerceevolution.catalog.application.usecase.media.updatemedia;

import dev.chaunm.commerceevolution.catalog.presentation.media.updatemedia.UpdateMediaRequest;
import dev.chaunm.commerceevolution.catalog.presentation.media.updatemedia.UpdateMediaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UpdateMediaMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "mediaId", source = "mediaId")
    @Mapping(target = "url", source = "request.url")
    UpdateMediaCommand toCommand(UUID productId, UUID mediaId, UpdateMediaRequest request);

    UpdateMediaResponse toResponse(UpdateMediaResult result);
}
