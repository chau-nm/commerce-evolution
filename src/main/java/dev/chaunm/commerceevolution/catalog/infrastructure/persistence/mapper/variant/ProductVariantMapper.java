package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.variant;

import dev.chaunm.commerceevolution.catalog.domain.model.variant.ProductVariant;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.SKU;
import dev.chaunm.commerceevolution.catalog.domain.model.variant.valueobject.VariantId;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.variant.ProductVariantEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    ProductVariant toDomain(ProductVariantEntity entity);

    ProductVariantEntity toEntity(ProductVariant domain);

    default UUID toUuid(VariantId id) {
        return id == null ? null : id.value();
    }

    default VariantId toVariantId(UUID value) {
        return value == null ? null : new VariantId(value);
    }

    default String toSkuValue(SKU sku) {
        return sku == null ? null : sku.value();
    }

    default SKU toSku(String value) {
        return value == null ? null : new SKU(value);
    }
}
