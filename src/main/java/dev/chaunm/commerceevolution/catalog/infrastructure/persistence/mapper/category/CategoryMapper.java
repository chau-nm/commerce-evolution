package dev.chaunm.commerceevolution.catalog.infrastructure.persistence.mapper.category;

import dev.chaunm.commerceevolution.catalog.domain.model.category.Category;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryId;
import dev.chaunm.commerceevolution.catalog.domain.model.category.valueobject.CategoryName;
import dev.chaunm.commerceevolution.catalog.infrastructure.persistence.entity.category.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toDomain(CategoryEntity entity);

    CategoryEntity toEntity(Category domain);

    default UUID toUuid(CategoryId id) {
        return id == null ? null : id.value();
    }

    default CategoryId toCategoryId(UUID value) {
        return value == null ? null : new CategoryId(value);
    }

    default String toNameValue(CategoryName name) {
        return name == null ? null : name.value();
    }

    default CategoryName toCategoryName(String value) {
        return value == null ? null : new CategoryName(value);
    }
}
