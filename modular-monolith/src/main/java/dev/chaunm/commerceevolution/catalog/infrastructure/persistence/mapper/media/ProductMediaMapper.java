package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.media;

import dev.chaunm.commerceevolution.catalog.domain.model.media.ProductMedia;
import dev.chaunm.commerceevolution.catalog.domain.model.media.valueobject.MediaId;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.media.ProductMediaEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMediaMapper {

    ProductMedia toDomain(ProductMediaEntity entity);

    ProductMediaEntity toEntity(ProductMedia domain);

    default UUID toUuid(MediaId id) {
        return id == null ? null : id.value();
    }

    default MediaId toMediaId(UUID value) {
        return value == null ? null : new MediaId(value);
    }
}
