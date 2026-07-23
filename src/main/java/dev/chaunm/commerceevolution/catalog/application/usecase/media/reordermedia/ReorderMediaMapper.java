package dev.chaunm.commerceevolution.catalog.application.usecase.media.reordermedia;

import dev.chaunm.commerceevolution.catalog.presentation.media.reordermedia.ReorderMediaRequest;
import dev.chaunm.commerceevolution.catalog.presentation.media.reordermedia.ReorderMediaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReorderMediaMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "mediaIds", source = "request.mediaIds")
    ReorderMediaCommand toCommand(UUID productId, ReorderMediaRequest request);

    ReorderMediaResponse toResponse(ReorderMediaResult result);
}
