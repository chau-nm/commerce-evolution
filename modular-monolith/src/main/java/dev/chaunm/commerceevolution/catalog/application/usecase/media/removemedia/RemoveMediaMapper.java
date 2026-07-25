package dev.chaunm.commerceevolution.catalog.application.usecase.media.removemedia;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RemoveMediaMapper {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "mediaId", source = "mediaId")
    RemoveMediaCommand toCommand(UUID productId, UUID mediaId);
}
