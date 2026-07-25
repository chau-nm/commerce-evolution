package dev.chaunm.commerceevolution.catalog.application.usecase.media.setthumbnail;

import dev.chaunm.commerceevolution.catalog.presentation.media.setthumbnail.SetThumbnailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SetThumbnailMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "mediaId", source = "mediaId")
    SetThumbnailCommand toCommand(UUID productId, UUID mediaId);

    SetThumbnailResponse toResponse(SetThumbnailResult result);
}
