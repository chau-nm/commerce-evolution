package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper;

import dev.chaunm.commerceevolution.catalog.domain.model.ProductMedia;
import dev.chaunm.commerceevolution.catalog.domain.model.valueobject.MediaId;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.ProductMediaEntity;
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
