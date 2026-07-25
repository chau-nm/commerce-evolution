package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.brand;

import dev.chaunm.commerceevolution.catalog.domain.model.brand.Brand;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.brand.valueobject.BrandName;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.brand.BrandEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toDomain(BrandEntity entity);

    BrandEntity toEntity(Brand domain);

    default UUID toUuid(BrandId id) {
        return id == null ? null : id.value();
    }

    default BrandId toBrandId(UUID value) {
        return value == null ? null : new BrandId(value);
    }

    default String toNameValue(BrandName name) {
        return name == null ? null : name.value();
    }

    default BrandName toBrandName(String value) {
        return value == null ? null : new BrandName(value);
    }
}
