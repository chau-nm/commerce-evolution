package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.product;

import dev.chaunm.commerceevolution.catalog.domain.model.product.Product;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.BrandId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductId;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.ProductName;
import dev.chaunm.commerceevolution.catalog.domain.model.product.valueobject.Slug;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.product.ProductEntity;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.media.ProductMediaMapper;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.variant.ProductVariantMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {ProductVariantMapper.class, ProductMediaMapper.class}
)
public interface ProductMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);

    @AfterMapping
    default void linkChildren(@MappingTarget ProductEntity entity) {
        entity.getVariants().forEach(variant -> variant.setProduct(entity));
        entity.getMedias().forEach(media -> media.setProduct(entity));
    }

    default UUID toUuid(ProductId id) {
        return id == null ? null : id.value();
    }

    default ProductId toProductId(UUID value) {
        return value == null ? null : new ProductId(value);
    }

    default String toNameValue(ProductName name) {
        return name == null ? null : name.value();
    }

    default ProductName toProductName(String value) {
        return value == null ? null : new ProductName(value);
    }

    default String toSlugValue(Slug slug) {
        return slug == null ? null : slug.value();
    }

    default Slug toSlug(String value) {
        return value == null ? null : new Slug(value);
    }

    default UUID toUuid(CategoryId id) {
        return id == null ? null : id.value();
    }

    default CategoryId toCategoryId(UUID value) {
        return value == null ? null : new CategoryId(value);
    }

    default UUID toUuid(BrandId id) {
        return id == null ? null : id.value();
    }

    default BrandId toBrandId(UUID value) {
        return value == null ? null : new BrandId(value);
    }
}
